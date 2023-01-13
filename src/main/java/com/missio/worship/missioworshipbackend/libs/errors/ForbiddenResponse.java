package com.missio.worship.missioworshipbackend.libs.errors;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ForbiddenResponse extends HttpErrorResponse {
    protected ForbiddenResponse(String error) {
        super(Instant.now(), HttpStatus.FORBIDDEN.value(), error);
    }
}
