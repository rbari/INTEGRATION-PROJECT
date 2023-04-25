package ZipReceiverKaftka.ZipReceiverKaftka;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;


@Component
public class ZipReceiver {

//	private static final String ZIP_FILE_NAME = "received.zip";

    @KafkaListener(topics = "my-zip-file-topic", groupId = "my-group")
    public void receiveZipFile(@Payload byte[] message, @Headers MessageHeaders headers) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(message))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                File file = new File(zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    byte[] buffer = new byte[1024];
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

}
