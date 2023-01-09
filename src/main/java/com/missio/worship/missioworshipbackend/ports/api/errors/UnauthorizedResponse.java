package com.missio.worship.missioworshipbackend.ports.api.errors;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class UnauthorizedResponse extends HttpErrorResponse {
    public UnauthorizedResponse(String reason) {
        super(Instant.now(), HttpStatus.UNAUTHORIZED.value(), reason);
    }
}