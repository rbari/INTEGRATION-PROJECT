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
	            ObjectMapper mapper = new ObjectMapper();
	            CreateServiceData createService = mapper.readValue(message, CreateServiceData.class);

	            System.out.println("Receiving");
	            System.out.println(createService.getTopicName());
	            RequestWrapper rq = new RequestWrapper("Testing", createService.getTopicName());
	            zipController.downloadZip("SS-14-15");
	            sender.send("fileunziped", rq);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException(e);
	        }
	    }
		  
//		  ObjectMapper mapper = new ObjectMapper();
//		  RequestWrapper requestWrapper = mapper.readValue(message, RequestWrapper.class);
//		  RequestWrapper rq = new RequestWrapper("Testing", requestWrapper.getServiceName());
//		  
//		  
//		  try {
//
//	            System.out.println("Receiving");
//	            System.out.println(requestWrapper);
//	    		zipController.holamundo(requestWrapper.getServiceName());
//	    		sender.send("fileunziped", rq);
//
//	        } catch (JsonProcessingException e) {
//	            throw new RuntimeException(e);
//	        }
}
	  
