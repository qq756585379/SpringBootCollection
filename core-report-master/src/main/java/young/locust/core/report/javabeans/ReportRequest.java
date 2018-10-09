package young.locust.core.report.javabeans;

import lombok.Data;

@Data
public class ReportRequest {
    private String taskId;
    private String reportName;
    private String reportSql;
    private Object[] params;
    private String[] keys;
    private String[] excelHeader;
}
