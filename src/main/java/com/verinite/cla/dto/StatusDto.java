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

	public StatusDto(String status, String preRunStatus, String preReportUrl, String postRunStatus,
			String postReportUrl) {
		super();
		this.status = status;
		this.preRunStatus = preRunStatus;
		this.preReportUrl = preReportUrl;
		this.postRunStatus = postRunStatus;
		this.postReportUrl = postReportUrl;
	}

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
}
