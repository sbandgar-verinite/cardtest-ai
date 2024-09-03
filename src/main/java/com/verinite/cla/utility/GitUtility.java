package com.verinite.cla.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.util.PropertiesConfig;
import com.verinite.commons.dto.StatusResponse;

@Component
public class GitUtility {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private PropertiesConfig propsConfig;
	
	@Autowired
	private RestTemplate restTemplate;
	

	public StatusResponse uploadFeatureFileToGit(String repoFileName,String contents) throws Exception {

		try {
			byte[] encoded = Base64.encodeBase64(contents.getBytes(), false);
			String encodedFileContent = new String(encoded, StandardCharsets.UTF_8);
			System.out.println("Base64 encoded: " + encodedFileContent);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setBearerAuth(propsConfig.getGitFeatureRepoAuthToken());
			HttpEntity<String> entity = new HttpEntity<String>(headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(repoFileName, HttpMethod.GET, entity,
					String.class);

			String sha = "";
			String url = "";
			if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
				JsonNode root = mapper.readTree(responseEntity.getBody());
				sha = root.path("sha").asText();
				url = root.path("url").asText();
			}
			System.out.println("SHA & URL: " + sha + " " + url);
			String baseURL = repoFileName + "?ref=" + propsConfig.getGitFeatureRepobranch();
			JSONObject bodyParam = new JSONObject();
			JSONObject committer = new JSONObject();
			committer.put("name", "Sumeet Bandgar");
			committer.put("email", "sumeet.bandgar@verinite.com");
			bodyParam.put("message", "Commit for Project" + Math.random());
			bodyParam.put("content", encodedFileContent);
			bodyParam.put("sha", sha);
			bodyParam.put("branch", "main");
			bodyParam.put("committer", committer);

			System.out.println("Body param: " + bodyParam.toString());
			HttpHeaders headers1 = new HttpHeaders();
			headers1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers1.setBearerAuth(propsConfig.getGitFeatureRepoAuthToken());
			HttpEntity<String> entity1 = new HttpEntity<>(bodyParam.toString(), headers1);

			ResponseEntity<Object> responseEntity1 = restTemplate.exchange(baseURL, HttpMethod.PUT, entity1,
					new ParameterizedTypeReference<Object>() {
					});

			if (responseEntity1.getStatusCode().is2xxSuccessful())
				return new StatusResponse("File uploaded succesfully", null, contents);
		} catch (Exception e) {
			throw e;
		}
		return new StatusResponse("File upload Failed", null, contents);

	}
	
    public String downloadFile(String fileURL) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(fileURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // Set the required headers
        connection.setRequestProperty("Accept", "application/vnd.github.v3.raw");
        connection.setRequestProperty("Authorization", "Bearer " + propsConfig.getGitFeatureRepoAuthToken());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

}
