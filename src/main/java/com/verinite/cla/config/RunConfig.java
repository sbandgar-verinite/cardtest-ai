package com.verinite.cla.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RunConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("run_type")
	private String runType;

	@JsonProperty("no_of_days_req")
	private Integer noOfDaysReq;

	public String getRunType() {
		return runType;
	}

	public void setRunType(String runType) {
		this.runType = runType;
	}

	public Integer getNoOfDaysReq() {
		return noOfDaysReq;
	}

	public void setNoOfDaysReq(Integer noOfDaysReq) {
		this.noOfDaysReq = noOfDaysReq;
	}

}
