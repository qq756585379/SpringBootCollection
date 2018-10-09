package young.locust.core.report.service.serviceimpl;

import lombok.extern.java.Log;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import young.locust.core.report.commons.Constants;
import young.locust.core.report.commons.utils.file.FileUtil;
import young.locust.core.report.commons.utils.sftp.SFTPUtil;
import young.locust.core.report.service.CoreReportService;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
@Service
public class CoreReportServiceImpl implements CoreReportService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final Integer size = 500;// 游标移动大小

    @Override
    public void createReportExcelFile(String taskId, String reportName,
                                      String reportSql, Object[] params,
                                      String[] keys, String[] headers) {
        // 获取总记录数
        final Integer count = getCount(reportSql, params);

        int keySize = keys.length;

        // 创建 一个excel文档对象
        HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
        HSSFSheet sheet = createExcelFile(workBook, reportName, headers, keySize);

        // 边读边写
        jdbcTemplate.execute(reportSql, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                if (params != null) {
                    int length = params.length;
                    for (int i = 0; i < length; i++) {
                        ps.setObject(i + 1, params[i]);
                    }
                }
                ResultSet rs = ps.executeQuery();

                int loopCount = count / size;// 循环次数 int
                int percent = 100 / (loopCount == 0 ? 1 : loopCount); // 返回百分比

                int loopSize = size;
                for (int k = 0; k <= loopCount; k++) {
                    if (k == loopCount) {
                        percent = 100 - (percent * loopCount);
                        loopSize = count - (k * size);// 处理最后一次写入不够size基数
                    }

                    for (int i = 0; i < loopSize; i++) {
                        // 获取数据
                        Object[] rowData = getReportData(rs, keys, keySize);

                        // 从第几行开始写入excel
                        int index = (k * size) + i + 1;
                        writeDataToFile(index, rowData, sheet);
                    }
                    // 通过消息中间件通知前端写入百分比，taskId,percent
                }

                String localFilePath = Constants.FILE_DIR_LOCAL + reportName;
                FileOutputStream fout = null;
                try {
                    // 将文件保存本地，上传sftp
                    fout = new FileOutputStream(localFilePath);
                    workBook.write(fout);
                    fout.close();

                    // SFTP文件上传
                    SFTPUtil.upload(Constants.FILE_DIR_CUSTOMER_ACCOUNT_FINANCE, reportName, localFilePath);

                    // 删除本地文件
                    FileUtil.deleteFile(localFilePath);
                } catch (Exception e) {
                    log.info("【文件上传失败】" + e);
                }

                return null;
            }

            private void writeDataToFile(int index, Object[] rowData, HSSFSheet sheet) {
                HSSFRow row = sheet.createRow(index); // 从第几行开始创建数据
                int length = rowData.length;
                for (int i = 0; i < length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(objectToString(rowData[i]));
                }
            }

            private String objectToString(Object object) {
                return object != null ? String.valueOf(object) : "";
            }

            private Object[] getReportData(ResultSet rs, String[] keys, Integer size) throws SQLException {
                rs.next();
                Object[] rowData = new Object[size];
                for (int i = 0; i < size; i++) {
                    rowData[i] = rs.getObject(keys[i]);
                }
                return rowData;
            }

        });
    }

    private HSSFSheet createExcelFile(HSSFWorkbook workBook, String reportName, String[] headers, Integer keySize) {
        HSSFSheet sheet = workBook.createSheet(reportName);// 创建一个工作薄对象
        HSSFRow row = sheet.createRow(0);// 创建表头
        for (int i = 0; i < keySize; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        return sheet;
    }

    private Integer getCount(String reportSql, Object[] params) {
        String countSql = "select count (0) total from (" + reportSql + ") t";
        Integer count = jdbcTemplate.queryForObject(countSql, params, Integer.class);
        return count;
    }
}
