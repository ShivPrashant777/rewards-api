package com.rewardsapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * Main Exception Handling Class for handling all exception in the application
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerNotFoundException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.NOT_FOUND);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest request) {
		String message = ex.getMessage();
		Map<String, String> response = createResponse(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	public Map<String, String> createResponse(String message, HttpStatus status) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("status", status.toString());
		return response;
	}
}
