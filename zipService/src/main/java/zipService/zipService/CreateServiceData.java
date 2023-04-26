package zipService.zipService;

import java.io.Serializable;

public class CreateServiceData implements Serializable {
private String topicName;
private long topicInterval;
public CreateServiceData(String topicName, long topicInterval) {
	super();
	this.topicName = topicName;
	this.topicInterval = topicInterval;
}
public CreateServiceData() {
	super();
	// TODO Auto-generated constructor stub
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