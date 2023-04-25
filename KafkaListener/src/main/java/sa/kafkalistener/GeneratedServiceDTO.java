package sa.kafkalistener;

import java.util.Set;

public class GeneratedServiceDTO {
    private String serviceName;
    private Set<String> topics;

    public GeneratedServiceDTO() {
    }

    public GeneratedServiceDTO(String serviceName, Set<String> topics) {
        this.serviceName = serviceName;
        this.topics = topics;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }
}
