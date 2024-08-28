package com.verinite.cla.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CamundaResponse {

	@JsonProperty("processInstanceKey")
	private Long processInstanceKey;

	@JsonProperty("bpmnProcessId")
	private String bpmnProcessId;

	@JsonProperty("nextTask")
	private NextTask nextTask;

	public Long getProcessInstanceKey() {
		return processInstanceKey;
	}

	public void setProcessInstanceKey(Long processInstanceKey) {
		this.processInstanceKey = processInstanceKey;
	}

	public String getBpmnProcessId() {
		return bpmnProcessId;
	}

	public void setBpmnProcessId(String bpmnProcessId) {
		this.bpmnProcessId = bpmnProcessId;
	}

	public NextTask getNextTask() {
		return nextTask;
	}

	public void setNextTask(NextTask nextTask) {
		this.nextTask = nextTask;
	}
}
