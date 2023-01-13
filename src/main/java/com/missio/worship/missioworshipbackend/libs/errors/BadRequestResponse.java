package com.missio.worship.missioworshipbackend.libs.errors;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class BadRequestResponse extends HttpErrorResponse {
    public BadRequestResponse(String error) {
        super(Instant.now(), HttpStatus.BAD_REQUEST.value(), error);
    }
}
