package UnzipService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
public class UnzipService {

    @Autowired
    private Sender sender;

    private static final int BUFFER_SIZE = 4096;

    private static final String LOCAL_DIR = System.getProperty("java.io.tmpdir");

    @KafkaListener(topics = "filedownloaded", groupId = "default")
    public void receive(@Payload String message, @Headers MessageHeaders headers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("got a message from client: "+ message);
        sender.send("fileunziped","The file has been unzipped");
//        try {
//            RequestWrapper requestWrapper = mapper.readValue(message, RequestWrapper.class);
//            sender.send("fileunziped","The file has been unzipped");
//            String zipFilePath = requestWrapper.getZipFilePath();
//            String serviceName = requestWrapper.getServiceName();
//            Set<String> topics = requestWrapper.getTopics();
//            byte[] file = null;
//
//            file = new ClassPathResource(zipFilePath).getInputStream().readAllBytes();
//            if(file != null){
//                String unzipPath = unzip(file);
//                RequestWrapper responseWrapper = new RequestWrapper(unzipPath,serviceName,topics);
//                System.out.println("sending to kafka "+ responseWrapper );
//                String jsonInString = mapper.writeValueAsString(responseWrapper);
//                sender.send("fileunziped",jsonInString);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public String unzip(byte[] file) throws IOException {

        String destDirectory = LOCAL_DIR + File.separator + UUID.randomUUID();
        File destDir = new File(destDirectory);
        destDir.mkdir();

        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(file));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            if(entry.getName().startsWith(".")) {
                entry = zipIn.getNextEntry();
                continue;
            }
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        return destDirectory;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}