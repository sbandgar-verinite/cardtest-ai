package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class StatusDto {

	private String preRunStatus;
	private String preReportUrl;
	private String postRunStatus;
	private String postReportUrl;

	public StatusDto(String preRunStatus, String preReportUrl, String postRunStatus, String postReportUrl) {
		super();
		this.preRunStatus = preRunStatus;
		this.preReportUrl = preReportUrl;
		this.postRunStatus = postRunStatus;
		this.postReportUrl = postReportUrl;
	}

	public StatusDto() {
		super();
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
