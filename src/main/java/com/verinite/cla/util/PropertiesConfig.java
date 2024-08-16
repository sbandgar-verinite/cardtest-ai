package com.verinite.cla.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:message.properties")
public class PropertiesConfig {

	@Value("${feature_file_created_successfully}")
	private String featureFileCreatedSuccessfully;

	@Value("${github.api.repo.url}")
	private String gitFeatureRepoUrl;

	@Value("${github.api.repo.branch}")
	private String gitFeatureRepobranch;

	@Value("${github.api.repo.auth.token}")
	private String gitFeatureRepoAuthToken;

	@Value("${jenkins.job.name}")
	private String jenkinsJobName;

	@Value("${jenkins.baseUrl}")
	private String jenkinsUrl;

	@Value("${jenkins.creds}")
	private String jenkinsCreds;

	@Value("${host.ip}")
	private String hostUrl;

	public String getHostUrl() {
		return hostUrl;
	}

	public String getJenkinsCreds() {
		return jenkinsCreds;
	}

	public String getJenkinsUrl() {
		return jenkinsUrl;
	}

	public String getJenkinsJobName() {
		return jenkinsJobName;
	}

	public String getGitFeatureRepoAuthToken() {
		return gitFeatureRepoAuthToken;
	}

	public String getGitFeatureRepoUrl() {
		return gitFeatureRepoUrl;
	}

	public String getGitFeatureRepobranch() {
		return gitFeatureRepobranch;
	}

	public String getFeatureFileCreatedSuccessfully() {
		return featureFileCreatedSuccessfully;
	}

}