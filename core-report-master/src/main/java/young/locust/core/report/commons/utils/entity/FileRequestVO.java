package young.locust.core.report.commons.utils.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class FileRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;

    private String filePath;
}
