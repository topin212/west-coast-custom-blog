package com.github.topin212.web.sboot.blog.entities.responseobjects;

import com.github.topin212.web.sboot.blog.exceptions.ApplicationException;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class ProcedureStatusResponse {
    private final String procedure;
    private final String status;

    private final Object result;
    private final ApplicationException applicationException;
    private final OffsetDateTime offsetDateTime;

    public ProcedureStatusResponse(String procedure, Object result) {
        this.procedure = procedure;
        this.status = "success";
        this.result = result;
        this.applicationException = null;
        offsetDateTime = OffsetDateTime.now(ZoneId.of("UTC"));
    }

    public ProcedureStatusResponse(ApplicationException applicationException) {
        this.procedure = applicationException.getMessage();
        this.status = "fail";
        this.result = "none";
        this.applicationException = applicationException;
        offsetDateTime = OffsetDateTime.now(ZoneId.of("UTC"));
    }

    public String getProcedure() {
        return procedure;
    }

    public String getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }
}
