package com.miu.sa.mvnservice.config;

public class CsInfo {

    private String topicName;
    private long topicInterval;

    public CsInfo() {
    }

    public CsInfo(String topicName, long topicInterval) {
        this.topicInterval = topicInterval;
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public long getTopicInterval() {
        return topicInterval;
    }

    public void setTopicInterval(long topicInterval) {
        this.topicInterval = topicInterval;
    }
}
