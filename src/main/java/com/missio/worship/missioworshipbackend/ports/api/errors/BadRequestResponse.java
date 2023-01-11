package com.missio.worship.missioworshipbackend.ports.api.errors;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class BadRequestResponse extends HttpErrorResponse{
    protected BadRequestResponse(String error) {
        super(Instant.now(), HttpStatus.BAD_REQUEST.value(), error);
    }
}
