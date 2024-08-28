package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CamundaRequest {

	@JsonProperty("project_id")
	private String projectId;

	@JsonProperty("runplan_id")
	private String runPlanId;

	@JsonProperty("type")
	private String type;

	@JsonProperty("pre_approved")
	private Boolean preApproved;

	@JsonProperty("post_approved")
	private Boolean postApproved;

	@JsonProperty("batch_approved")
	private Boolean batchApproved;

	@JsonProperty("is_batch")
	private Boolean isBatch;

	public CamundaRequest() {
		super();
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRunPlanId() {
		return runPlanId;
	}

	public void setRunPlanId(String runPlanId) {
		this.runPlanId = runPlanId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getPreApproved() {
		return preApproved;
	}

	public void setPreApproved(Boolean preApproved) {
		this.preApproved = preApproved;
	}

	public Boolean getPostApproved() {
		return postApproved;
	}

	public void setPostApproved(Boolean postApproved) {
		this.postApproved = postApproved;
	}

	public Boolean getIsBatch() {
		return isBatch;
	}

	public void setIsBatch(Boolean isBatch) {
		this.isBatch = isBatch;
	}

	public Boolean getBatchApproved() {
		return batchApproved;
	}

	public void setBatchApproved(Boolean batchApproved) {
		this.batchApproved = batchApproved;
	}
}
