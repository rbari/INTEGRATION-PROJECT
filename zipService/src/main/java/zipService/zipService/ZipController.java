package zipService.zipService;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ZipController {
	

	@Autowired
	ZipClient zipClient;
	@Autowired
	private Sender sender;
//	@Autowired
//	private ZipClient zipClient;
	
    @GetMapping(value = "/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadZip(@RequestParam("service-name") String serviceName ) {
    	
    	Resource resource = zipClient.downloadFile( serviceName);
        try(InputStream inputStream = resource.getInputStream()) {
        	 byte[] data = IOUtils.toByteArray(inputStream);
             ByteArrayResource resourceResponse = new ByteArrayResource(data);
             RequestWrapper rWrapper = new RequestWrapper(serviceName, serviceName);
             sender.send("download", rWrapper);
            return ResponseEntity.ok()
            		 .contentLength(data.length)
                     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + serviceName + ".zip")
                     .contentType(MediaType.APPLICATION_OCTET_STREAM)
                     .body(resourceResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
