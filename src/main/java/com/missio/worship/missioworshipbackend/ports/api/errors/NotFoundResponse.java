package com.missio.worship.missioworshipbackend.ports.api.errors;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class NotFoundResponse extends HttpErrorResponse {

    public NotFoundResponse(String reason) {
        super(Instant.now(), HttpStatus.NOT_FOUND.value(), reason);
    }
}
