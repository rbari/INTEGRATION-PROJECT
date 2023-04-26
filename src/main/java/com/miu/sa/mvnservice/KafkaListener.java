package com.miu.sa.mvnservice;

import com.miu.sa.mvnservice.config.RequestWrapper;
import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class KafkaListener {

    private static Integer port = 9900;

    @Autowired
    private Environment env;

    private String mvnHome;

    @org.springframework.kafka.annotation.KafkaListener(topics = "fileunziped", groupId = "default")
    public void listenForDeployment(RequestWrapper requestWrapper) {
        System.out.println(requestWrapper);
        executeMvn(requestWrapper);
    }

    private void executeMvn(RequestWrapper requestWrapper) {
        execute(requestWrapper.getServiceName(), requestWrapper.getZipFilePath(), Arrays.asList("clean", "spring-boot:run " +
                "-Dspring-boot.run.jvmArguments=\"-Dserver.port=" + port++ + " -Ddemo.topic=" + String.join("-", requestWrapper.getTopics()) + "\""));
    }

    private void execute(String serviceName, String dirPath, List<String> goals) {
        try {
            int p = port - 1;
            InvocationRequest request = new DefaultInvocationRequest();
            request.setBaseDirectory(new File(dirPath));
            request.setGoals(goals);

            String os = System.getProperty("os.name");

            if (os.contains("Mac")) {
                mvnHome = env.getProperty("mvn.home.mac");
            } else {
                mvnHome = env.getProperty("mvn.home.windows");
            }

            request.setMavenHome(new File(mvnHome));
            request.setBatchMode(false);

            request.setOutputHandler(s -> {});

            Invoker invoker = new DefaultInvoker();
            new Thread(() -> {
                try {
                    InvocationResult result = invoker.execute(request);
                    if (result.getExecutionException() != null) {
                        result.getExecutionException().printStackTrace();
                    }
                } catch (MavenInvocationException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("Service: " + serviceName + " running in port: " + p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
