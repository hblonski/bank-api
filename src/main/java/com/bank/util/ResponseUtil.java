package com.bank.util;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;

public final class ResponseUtil {

    private ResponseUtil() {
        // Empty
    }

    public static Response handleGenericResponse(Object body, Response.Status status) {
        return Response.status(status)
                .entity(body)
                .build();
    }

    public static Response handleConstraintViolationException(ConstraintViolationException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ValidatorUtil.createValidationMessage(e.getConstraintViolations()))
                .build();
    }

    public static Response handleGenericException(Exception e, Response.Status status) {
        return Response.status(status)
                .entity(e.getMessage())
                .build();
    }
}
