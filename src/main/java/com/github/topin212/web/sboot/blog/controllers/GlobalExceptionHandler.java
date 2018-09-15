package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.web.sboot.blog.entities.responseobjects.ErrorResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handle(WebRequest request, Throwable t) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(t.getMessage(), request.getContextPath());

        return new ResponseEntity<>(mapper.writeValueAsString(errorResponse), HttpStatus.EXPECTATION_FAILED);
    }
}
