package zipService.zipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

public class Receiver {
	
	@Autowired
	ZipController zipController;
	
	@KafkaListener(topics = "tester", groupId = "default")
	public void receive(@Payload String serviceName,
	@Headers MessageHeaders headers) {
//		zipController.downloadZip(serviceName);
		zipController.holamundo(serviceName);
		
	}

}
