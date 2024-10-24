package com.verinite.cla.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.verinite.cla.entity.ReportHistory;
import com.verinite.cla.repository.ReportHistoryRepository; 
import com.verinite.cla.service.ExternalService;
import com.verinite.cla.service.LlamaAiService;
import com.verinite.cla.service.RunPlanService;
import com.verinite.cla.util.PropertiesConfig;
import com.verinite.cla.util.RunPlanStatus;
import com.verinite.cla.util.ZipUtil;
import com.verinite.commons.controlleradvice.BadRequestException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ExternalController {

    private static final Logger logger = Logger.getLogger(ExternalController.class.getName());
    

    @Autowired
    private RunPlanService runPlanService;

    @Autowired
    private LlamaAiService llamaAiService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertiesConfig propsConfig;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private ReportHistoryRepository reportHistoryRepository;  

    @PostMapping("notify/build")
    public void notify(@RequestBody JsonNode jsonObj) throws IOException {
        if (jsonObj != null) {
            String status = jsonObj.get("status").asText();
            String buildNumber = jsonObj.get("buildNumber").asText();
            String fileName = jsonObj.get("fileName").asText();  
            String runPlanId = jsonObj.get("runPlanId").asText();
            String type = fileName.substring(fileName.lastIndexOf('-') + 1).toLowerCase(); 
            logger.info("Status : " + status + " Build Number : " + buildNumber + " fileName : " + fileName + " runPlanId : " + runPlanId);

            if (status.equalsIgnoreCase("Success")) {
                byte[] plainCredsBytes = propsConfig.getJenkinsCreds().getBytes();
                byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes, false);
                String base64Creds = new String(base64CredsBytes);

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Basic " + base64Creds);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Resource> result = restTemplate.exchange(
                    propsConfig.getJenkinsUrl() + "/job/" + propsConfig.getJenkinsJobName() + "/" + buildNumber + "/artifact/target/site/serenity/*zip*/serenity.zip",
                    HttpMethod.GET, entity, Resource.class);

                if (result.getStatusCode().is2xxSuccessful()) {
                    logger.info(result.toString());

                   //latest sequence from report history - runplanId increment by 1
                   Long nextSequence = reportHistoryRepository.countByRunPlanId(runPlanId);
                   
                   if(nextSequence == 0)
                   {
                	   nextSequence = 1L ;
                   }
                   else
                   {
                	   nextSequence++;
                   }


                    String directoryPath = "static-files/" + runPlanId + "/" + type + "/" + nextSequence + "/";  

                    createDirectory(directoryPath);  

//                    String zipFileName = fileName.substring(0, fileName.lastIndexOf('.')); 
//                    String zipFilePath = directoryPath +  ;
//                    		+ fileName + ".zip";  

                    try (InputStream inputStream = result.getBody().getInputStream();
                         OutputStream outputStream = new FileOutputStream(directoryPath + "/" + fileName + ".zip")) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); 
                    }
                    
                  
                    ReportHistory history = new ReportHistory();
                    history.setSequence(nextSequence);
                    history.setRunPlanId(runPlanId);
                    history.setType(type);

                    history.setUrl(propsConfig.getHostUrl() + "/api/v1/cardtest/"+ directoryPath + "/" + fileName + "/serenity/index.html");
                    history.setDate(LocalDate.now());
                    
                    reportHistoryRepository.save(history);

                    File destDir = new File(directoryPath);  
                    ZipUtil.unzip(getFile(directoryPath + "/" + fileName + ".zip" ), destDir); 

                    runPlanService.updateStatus(runPlanId, RunPlanStatus.BUILD_SUCCESS.getStatus(),


                        propsConfig.getHostUrl() + "/api/v1/cardtest/"+ directoryPath + "/" + fileName + "/serenity/index.html", type);
                } else {
                    runPlanService.updateStatus(runPlanId, RunPlanStatus.BUILD_FAILED.getStatus(), null, type);
                }
            }
        }
    }

    public void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new BadRequestException("Failed to create directory."); 
            }
        }
    }

    public File getFile(String filePath) {
        createDirectory(filePath);
        return new File(filePath);
    }

    @PostMapping("/defect/gen")
    public ResponseEntity<Object> storeDefect(@RequestBody JsonNode defectDetails) {
        if (Objects.isNull(defectDetails)) {
            throw new BadRequestException("Object is null"); 
        }
        externalService.storeDefect(defectDetails); 
        return ResponseEntity.status(HttpStatus.OK).build(); 
    }
}
