package com.verinite.cla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getValidationErrors());
		error.setStatus("Input Validation Failed");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.FORBIDDEN.value());
		error.setMessage(ex.getValidationErrors());
		error.setStatus("Authorisation Failed");
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(ex.getValidationErrors());
		error.setStatus("Authentication Failed");
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<Object> handleInternalServerException(InternalServerException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getValidationErrors());
		error.setStatus("Server Failed");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
