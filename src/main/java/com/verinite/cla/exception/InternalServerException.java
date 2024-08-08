package com.verinite.cla.exception;

import java.util.Collections;
import java.util.List;

public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<String> errors;

	public InternalServerException(List<String> errors) {
		super("Validation Failed");
		this.errors = errors;
	}

	public InternalServerException(String message) {
		this(Collections.singletonList(message));
	}

	public List<String> getValidationErrors() {
		return errors;
	}

}
