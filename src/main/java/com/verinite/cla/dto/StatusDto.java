package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class StatusDto {

	@JsonProperty("status")
	private String status;

	@JsonProperty("pre_run_status")
	private String preRunStatus;

	@JsonProperty("pre_report_url")
	private String preReportUrl;

	@JsonProperty("post_run_status")
	private String postRunStatus;

	@JsonProperty("post_report_url")
	private String postReportUrl;

	@JsonProperty("batch_run_status")
	private String batchRunStatus;

	@JsonProperty("is_pre_exec_enable")
	private Boolean isPreExecEnable;

	@JsonProperty("is_post_exec_enable")
	private Boolean isPostExecEnable;

	@JsonProperty("is_pre_upload_enable")
	private Boolean isPreUploadEnable;

	@JsonProperty("is_post_upload_enable")
	private Boolean isPostUploadEnable;

	@JsonProperty("is_pre_verify")
	private Boolean isPreVerify;
	
	public StatusDto() {
		super();
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

	public String getBatchRunStatus() {
		return batchRunStatus;
	}

	public void setBatchRunStatus(String batchRunStatus) {
		this.batchRunStatus = batchRunStatus;
	}
}
