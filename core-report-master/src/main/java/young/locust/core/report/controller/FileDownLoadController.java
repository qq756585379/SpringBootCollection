package young.locust.core.report.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import young.locust.core.report.commons.Constants;
import young.locust.core.report.commons.utils.entity.FileRequestVO;
import young.locust.core.report.commons.utils.sftp.SFTPTool;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/*
 *  Document    : FileDownLoadController
 *  Created on  : 2017年11月14日
 *  Last Update : 2017年11月14日
 *  Author      : chenyb
 */
@Log
@Controller
@RequestMapping("/file")
public class FileDownLoadController {

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, FileRequestVO fileInfo)
            throws IOException {

        InputStream iStream = null;
        OutputStream outStrem = null;
        SFTPTool sftp = null;
        try {
            String name = fileInfo.getFileName();
            String path = Constants.FILE_DIR_CUSTOMER_ACCOUNT_FINANCE;

            response.setContentType("multipart/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
            sftp = new SFTPTool(Constants.SFTP_REQ_USERNAME, Constants.SFTP_REQ_PASSWORD,
                    Constants.SFTP_REQ_HOST, Constants.SFTP_DEFAULT_PORT);
            sftp.login();
            iStream = SFTPTool.download(path, name);
            outStrem = response.getOutputStream();

            byte[] buff = new byte[2048];
            int length = 0;

            while ((length = iStream.read(buff)) > 0) {
                outStrem.write(buff, 0, length);
                outStrem.flush();
            }

        } catch (Exception e) {
            log.info("文件下载失败" + e);
        } finally {
            if (sftp != null) {
                sftp.logout();
            }
            if (iStream != null) {
                iStream.close();
            }
        }
    }
}
