package com.che300.telephoneNumberAttributionQuerySystem.filter;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ExecutionException;

@RestControllerAdvice(basePackages = "com.che300.telephoneNumberAttributionQuerySystem.controller", assignableTypes = QueryExceptionHandler.class)
public class QueryExceptionHandler {
	
	@ExceptionHandler({ExecutionException.class, InterruptedException.class})
	@ResponseBody
	public ResponseEntity<String> clientQueryError(final Exception e) {
		return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String badRequest(final ConstraintViolationException e) {
		return e.getMessage();
	}
}
