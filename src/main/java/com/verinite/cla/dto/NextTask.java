package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NextTask {

	@JsonProperty("elementId")
	private String elementId;

	@JsonProperty("valueType")
	private String valueType;

	@JsonProperty("type")
	private String type;

	@JsonProperty("taskId")
	private Long taskId;

	@JsonProperty("status")
	private String status;

	public NextTask() {
		super();
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
