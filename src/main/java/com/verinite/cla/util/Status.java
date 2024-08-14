package com.verinite.cla.util;

public enum Status {

	CREATED("Created"), INPROGRESS("In-Progress"), COMPLETED("Completed");

	private String status;

	Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
