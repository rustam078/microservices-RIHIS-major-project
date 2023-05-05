package com.example.demo.ArException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ArExceptionHandle {

	@ExceptionHandler(value = ArException.class)
	public ResponseEntity<ArExceptionResponse> ArException(ArException msg){
		ArExceptionResponse arExceptionResponse = new ArExceptionResponse();
		arExceptionResponse.setExcode("AZX0001T");
		arExceptionResponse.setExmsg(msg.getMessage());
		return new ResponseEntity<ArExceptionResponse>(arExceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
