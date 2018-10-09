package young.locust.core.report.controller;

import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import young.locust.core.report.javabeans.ReportRequest;
import young.locust.core.report.service.CoreReportService;

import javax.annotation.Resource;

@Log
@RestController
@RequestMapping("/report")
public class CreateExcelReportController {

    @Resource
    private CoreReportService coreReportService;

    /**
     * sql写法注意点：
     * 1、sqlServer字符串拼接只能用“+”，前后端数据传递会有问题，使用“||”代替“+”
     * 2、like语句拼接：where a||b like '%'||?||'%'
     */
    @PostMapping("/create/excel")
    public void createExcelReport(ReportRequest report) throws Exception {

        String taskId = report.getTaskId();
        String reportName = report.getReportName();
        String reportSql = report.getReportSql();
        String[] keys = report.getKeys();
        String[] headers = report.getExcelHeader();

        if (StringUtils.isBlank(taskId) || StringUtils.isBlank(reportName) || StringUtils.isBlank(reportSql) || keys == null || headers == null) {
            log.info("【生成excel文件请求参数错误】" + taskId);
            throw new Exception("检查请求参数");
        }

        String sql = reportSql.replaceAll("\\|\\|", "+");

        new Thread(new Runnable() {
            @Override
            public void run() {
                coreReportService.createReportExcelFile(taskId, reportName.trim() + ".xlsx", sql, report.getParams(), keys, headers);
            }
        }).start();
    }
}
