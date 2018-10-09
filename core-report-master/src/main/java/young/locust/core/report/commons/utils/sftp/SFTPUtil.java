package young.locust.core.report.commons.utils.sftp;

import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import young.locust.core.report.commons.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName : SFTPUtil
 * @Description: SFTP上传的调用工具类
 * @date ： 2017年11月7日
 * @Author ：fanjy
 */
public class SFTPUtil {

    private static final Logger log = LoggerFactory.getLogger(SFTPUtil.class);

    /*
     * SFTP上传的调用工具类
     *
     * @param fileDirectory 上传到的文件目录
     *
     * @param uploadFile 上传到服务器文件名 jpa.doc
     *
     * @param orginfilename 输入流文件名 (本地文件)
     */
    public static boolean upload(String fileDirectory, String uploadFile, String orginfilename) throws SftpException, IOException {

        SFTPTool sftp = new SFTPTool(Constants.SFTP_REQ_USERNAME,
                Constants.SFTP_REQ_PASSWORD,
                Constants.SFTP_REQ_HOST,
                Constants.SFTP_DEFAULT_PORT);

        InputStream input = new FileInputStream(orginfilename);

        try {
            sftp.login();
            SFTPTool.upload(fileDirectory, uploadFile, input);
        } catch (Exception e) {
            log.info("sftp文件上传异常：" + e);
            return false;
        } finally {
            input.close();
            sftp.logout();
        }
        return true;
    }
}
