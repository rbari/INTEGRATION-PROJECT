package UnzipService;

public class RequestWrapper {

    private String zipFilePath;
    private String serviceName;

    RequestWrapper(){}

    public RequestWrapper(String zipFilePath, String serviceName) {
        this.zipFilePath = zipFilePath;
        this.serviceName = serviceName;
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return "RequestWrapper{" +
                "zipFilePath='" + zipFilePath + '\'' +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
