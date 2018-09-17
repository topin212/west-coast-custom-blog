package com.github.topin212.web.sboot.blog.exceptions;

public class ApplicationException extends Exception {

    String procedure;

    public ApplicationException(String message, String procedure) {
        super(message);
        this.procedure = procedure;
    }
}
