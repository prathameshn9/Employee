package com.Employee.Management.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	
	public static class ResourceNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ResourceNotFoundException(String message) {
			super(message);
		}
	}

	//json result for error
	private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String error, String message) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("status", status.value());
		errorResponse.put("error", error);
		errorResponse.put("message", message);
		errorResponse.put("timestamp", System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, status);
	}

	//404
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
	}

	//400
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequestException(IllegalArgumentException ex) {
		return createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
	}

	//403
	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<Map<String, Object>> handleForbiddenException(SecurityException ex) {
		return createErrorResponse(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage());
	}

	//500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
		return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage());
	}
}
