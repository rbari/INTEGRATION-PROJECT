package zipService.zipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class Receiver {
	
	@Autowired
	ZipController zipController;
	@Autowired
	Sender sender;
	
	  @KafkaListener(topics = "DSGS_CREATION", groupId = "default")
	public void receive(String message) throws JsonProcessingException {
		  System.out.println("hello world");
		  
		  try {

	            System.out.println("Receiving");
	            System.out.println(message);
	            RequestWrapper rq = new RequestWrapper("Testing", message);
	            zipController.downloadZip(message);
	            sender.send("fileunziped", rq);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException(e);
	        }
	    }
		  
}
	  
