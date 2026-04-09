package com.in.Excepitons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.in.Entitty.ErrorResponce;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	ResponseEntity<ErrorResponce> handleResources(ResourceNotFoundException ex){
		ErrorResponce err = new ErrorResponce(ex.getMessage(),HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

}
