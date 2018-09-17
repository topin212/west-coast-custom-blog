package com.github.topin212.web.sboot.blog.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.topin212.web.sboot.blog.entities.responseobjects.ProcedureStatusResponse;
import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ProcedureStatusResponse handle(WebRequest request, Throwable t) throws JsonProcessingException {
        return new ProcedureStatusResponse((ApplicationException) t);
    }
}
