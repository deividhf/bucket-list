package com.bucketlist.infrastructure.rest.exceptionhandler;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bucketlist.domain.shared.specification.InvalidSpecificationException;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> entityNotfound(EntityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(ex));
	} 
	
	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> illegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex));
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> invalidSpecificationException(InvalidSpecificationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex));
	}
}
