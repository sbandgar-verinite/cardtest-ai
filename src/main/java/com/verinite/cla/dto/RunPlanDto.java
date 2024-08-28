package com.verinite.cla.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.verinite.cla.model.RunScenario;

@JsonInclude(value = Include.NON_EMPTY)
public class RunPlanDto {

	@JsonProperty("id")
	private String id;

	@JsonProperty("sequence_number")
	private int sequenceNumber;

	@JsonProperty("project_id")
	private String projectId;

	@JsonProperty("description")
	private String description;

	@JsonProperty("run_date")
	private Long runDate;

	@JsonProperty("pre_run_scripts")
	private List<RunScenario> preRunScripts;

	@JsonProperty("post_run_scripts")
	private List<RunScenario> postRunScripts;

	@JsonProperty("status")
	private String status;

	@JsonProperty("pre_run_status")
	private String preRunStatus;

	@JsonProperty("post_run_status")
	private String postRunStatus;

	@JsonProperty("batch_run_status")
	private String batchRunStatus;

	@JsonProperty("pre_report_url")
	private String preReportUrl;

	@JsonProperty("post_report_url")
	private String postReportUrl;

	@JsonProperty("batch_job_enabled")
	private String batchJobEnabled;

	@JsonProperty("pre_run_task_id")
	private Long preRunTaskId;

	@JsonProperty("post_run_task_id")
	private Long postRunTaskId;

	@JsonProperty("batch_run_task_id")
	private Long batchRunTaskId;

	@JsonProperty("instance_id")
	private Long instanceId;

	@JsonProperty("is_pre_exec_enable")
	private Boolean isPreExecEnable;

	@JsonProperty("is_post_exec_enable")
	private Boolean isPostExecEnable;

	@JsonProperty("is_pre_upload_enable")
	private Boolean isPreUploadEnable;

	@JsonProperty("is_post_upload_enable")
	private Boolean isPostUploadEnable;

	public RunPlanDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRunDate() {
		return runDate;
	}

	public void setRunDate(Long runDate) {
		this.runDate = runDate;
	}

	public List<RunScenario> getPreRunScripts() {
		return preRunScripts;
	}

	public void setPreRunScripts(List<RunScenario> preRunScripts) {
		this.preRunScripts = preRunScripts;
	}

	public List<RunScenario> getPostRunScripts() {
		return postRunScripts;
	}

	public void setPostRunScripts(List<RunScenario> postRunScripts) {
		this.postRunScripts = postRunScripts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPreRunStatus() {
		return preRunStatus;
	}

	public void setPreRunStatus(String preRunStatus) {
		this.preRunStatus = preRunStatus;
	}

	public String getPostRunStatus() {
		return postRunStatus;
	}

	public void setPostRunStatus(String postRunStatus) {
		this.postRunStatus = postRunStatus;
	}

	public String getPreReportUrl() {
		return preReportUrl;
	}

	public void setPreReportUrl(String preReportUrl) {
		this.preReportUrl = preReportUrl;
	}

	public String getPostReportUrl() {
		return postReportUrl;
	}

	public void setPostReportUrl(String postReportUrl) {
		this.postReportUrl = postReportUrl;
	}

	public String getBatchJobEnabled() {
		return batchJobEnabled;
	}

	public void setBatchJobEnabled(String batchJobEnabled) {
		this.batchJobEnabled = batchJobEnabled;
	}

	public String getBatchRunStatus() {
		return batchRunStatus;
	}

	public void setBatchRunStatus(String batchRunStatus) {
		this.batchRunStatus = batchRunStatus;
	}

	public Long getPreRunTaskId() {
		return preRunTaskId;
	}

	public void setPreRunTaskId(Long preRunTaskId) {
		this.preRunTaskId = preRunTaskId;
	}

	public Long getPostRunTaskId() {
		return postRunTaskId;
	}

	public void setPostRunTaskId(Long postRunTaskId) {
		this.postRunTaskId = postRunTaskId;
	}

	public Long getBatchRunTaskId() {
		return batchRunTaskId;
	}

	public void setBatchRunTaskId(Long batchRunTaskId) {
		this.batchRunTaskId = batchRunTaskId;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Boolean getIsPreExecEnable() {
		return isPreExecEnable;
	}

	public void setIsPreExecEnable(Boolean isPreExecEnable) {
		this.isPreExecEnable = isPreExecEnable;
	}

	public Boolean getIsPostExecEnable() {
		return isPostExecEnable;
	}

	public void setIsPostExecEnable(Boolean isPostExecEnable) {
		this.isPostExecEnable = isPostExecEnable;
	}

	public Boolean getIsPreUploadEnable() {
		return isPreUploadEnable;
	}

	public void setIsPreUploadEnable(Boolean isPreUploadEnable) {
		this.isPreUploadEnable = isPreUploadEnable;
	}

	public Boolean getIsPostUploadEnable() {
		return isPostUploadEnable;
	}

	public void setIsPostUploadEnable(Boolean isPostUploadEnable) {
		this.isPostUploadEnable = isPostUploadEnable;
	}
}
