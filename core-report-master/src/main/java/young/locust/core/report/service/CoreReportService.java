package young.locust.core.report.service;

/*
 *  Document    : CoreReportService
 *  Created on  : 2017-12-5
 *  Last Update : 2017-12-5
 *  Author      : chenyb
 */
public interface CoreReportService {
    /**
     * 生成excel报表文件
     *
     * @param reportName 报表名称
     * @param reportSql  调用报表sql
     * @param params     请求参数
     * @param keys       通过key为获取sql返回结果数据，
     * @param headers    excel第一行表头
     */
    void createReportExcelFile(String taskId,
                               String reportName,
                               String reportSql,
                               Object[] params,
                               String[] keys,
                               String[] headers);
}
