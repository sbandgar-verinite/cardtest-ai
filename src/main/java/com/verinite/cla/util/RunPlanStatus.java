package com.verinite.cla.util;

public enum RunPlanStatus {

	BUILD_TRIGGERED("Build Triggered"), BUILD_SUCCESS("Build Success"), BUILD_FAILED("Build Failed");

	private String status;

	RunPlanStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
