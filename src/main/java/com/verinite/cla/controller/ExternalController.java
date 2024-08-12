package com.verinite.cla.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.verinite.cla.dto.GherkinFormat;
import com.verinite.cla.service.LlamaAiService;
import com.verinite.cla.service.RunPlanService;
import com.verinite.cla.util.RunPlanStatus;
import com.verinite.cla.util.ZipUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ExternalController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RunPlanService runPlanService;

	@Autowired
	private LlamaAiService llamaAiService;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${jenkins.baseUrl}")
	private String jenkinsUrl;

	@Value("${jenkins.creds}")
	private String creds;

	@GetMapping("api/v1/ai/generate")
	public ResponseEntity<GherkinFormat> generate(@RequestParam(value = "promptMessage") String promptMessage,
			@RequestParam(value = "count", defaultValue = "") String count) throws Exception {
		if (promptMessage == null) {
			throw new Exception("Please enter a valid prompt message");
		}
		final GherkinFormat aiResponse = llamaAiService.generateMessage(promptMessage, count);
		return ResponseEntity.status(HttpStatus.OK).body(aiResponse);
	}

	@PostMapping("notify/build")
	public void notify(@RequestBody JsonNode jsonObj) throws IOException {
		if (jsonObj != null) {
			String status = jsonObj.get("status").asText();
			String buildNumber = jsonObj.get("buildNumber").asText();
			String fileName = jsonObj.get("fileName").asText();
			String runPlanId = jsonObj.get("runPlanId").asText();
			logger.info("Status : " + status + "Build Number : " + buildNumber + "fileName :" + fileName,
					"runPlanId :" + runPlanId);

			if (status.equalsIgnoreCase("Success")) {
				byte[] plainCredsBytes = creds.getBytes();
				byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes, false);
				String base64Creds = new String(base64CredsBytes);

				HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization", "Basic " + base64Creds);
				HttpEntity<String> entity = new HttpEntity<String>(headers);

				ResponseEntity<Resource> result = restTemplate.exchange(
						jenkinsUrl + "/job/CARDTEST.AI/" + buildNumber
								+ "/artifact/target/site/serenity/*zip*/serenity.zip",
						HttpMethod.GET, entity, Resource.class);

				if (result.getStatusCode().is2xxSuccessful()) {
					logger.info(result.toString());
					try (InputStream inputStream = result.getBody().getInputStream();
							OutputStream outputStream = new FileOutputStream("static-files/" + fileName + ".zip")) {
						byte[] buffer = new byte[1024];
						int bytesRead;
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					File destDir = new File("static-files/" + fileName);
					ZipUtil.unzip(getFile("static-files/" + fileName + ".zip"), destDir);
				}
				runPlanService.updateStatus(runPlanId, RunPlanStatus.PRE_RUN_SUCCESS.name(),
						"http://localhost:8090/api/v1/cardtest/" + fileName + "/serenity/index.html");
			} else {
				runPlanService.updateStatus(runPlanId, RunPlanStatus.PRE_RUN_FAILURE.name(), null);
			}
		}
	}

	public File getFile(String filePath) {
		return new File(filePath);
	}
}
