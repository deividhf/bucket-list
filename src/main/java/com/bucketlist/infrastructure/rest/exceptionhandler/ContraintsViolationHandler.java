package com.bucketlist.infrastructure.rest.exceptionhandler;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bucketlist.infrastructure.rest.exceptionhandler.dto.ValidationErrorDTO;

@ControllerAdvice
public class ContraintsViolationHandler {
	
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
 
        return processViolations(violations);
    }
    
    private ValidationErrorDTO processViolations(Set<ConstraintViolation<?>> violations) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
 
        for (ConstraintViolation<?> fieldError: violations) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getPropertyPath().toString(), localizedErrorMessage);
        }
 
        return dto;
    }
 
    private String resolveLocalizedErrorMessage(ConstraintViolation<?> fieldError) {
        return fieldError.getMessage();
    }
}
