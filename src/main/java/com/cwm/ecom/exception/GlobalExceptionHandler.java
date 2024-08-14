package com.cwm.ecom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cwm.ecom.entity.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ApiResponse  handleProductNotFoundException(ProductNotFoundException ex) {
		
		ApiResponse response= ApiResponse.builder()
				.message(ex.getLocalizedMessage())
				.exception(ex.getClass().getSimpleName())
				.status(HttpStatus.NOT_FOUND)
				.statusCode(HttpStatus.NOT_FOUND.value())
				.build();
		return response;
	}
}
