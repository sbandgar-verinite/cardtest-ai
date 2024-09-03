package com.verinite.cla.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:message.properties")
public class PropertiesConfig {

	@Value("${feature_file_created_successfully}")
	private String featureFileCreatedSuccessfully;

	@Value("${github.api.repo.url}")
	private String gitFeatureRepoUrl;
	
	@Value("${github.api.repo.branch}")
	private String gitFeatureRepobranch;
	
	@Value("${github.api.repo.stepDefinitionUrl}")
	private String gitStepDefinitionUrl;

	
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

	@Value("${camunda.host.ip}")
	private String camundaHostId;

	@Value("${camunda.bpmn.process.id}")
	private String camundaBpmnProcessId;

	@Value("${github.code.path.stepdefinitions}")
	private String gitPathSetDefs;

	@Value("${github.code.path.stepdefinitions}")
	private String gitPathSteps;

	@Value("${github.code.path.pages}")
	private String gitPathPages;

	public String getGitPathSetDefs() {
		return gitPathSetDefs;
	}

	public String getGitPathSteps() {
		return gitPathSteps;
	}

	public String getGitPathPages() {
		return gitPathPages;
	}

	public String getCamundaHostId() {
		return camundaHostId;
	}

	public String getCamundaBpmnProcessId() {
		return camundaBpmnProcessId;
	}

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
	
	public String getGitStepDefinitionUrl() {
		return gitStepDefinitionUrl;
	}


	public String getGitFeatureRepobranch() {
		return gitFeatureRepobranch;
	}

	public String getFeatureFileCreatedSuccessfully() {
		return featureFileCreatedSuccessfully;
	}

}