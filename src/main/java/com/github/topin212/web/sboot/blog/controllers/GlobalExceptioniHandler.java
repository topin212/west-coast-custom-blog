package com.github.topin212.web.sboot.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptioniHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(WebRequest request, Throwable t){
        return new ResponseEntity<>(t.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }


}
