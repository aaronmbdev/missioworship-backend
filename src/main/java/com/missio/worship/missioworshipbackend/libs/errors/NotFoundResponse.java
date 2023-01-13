package com.missio.worship.missioworshipbackend.libs.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

public class NotFoundResponse extends HttpErrorResponse {

    public NotFoundResponse(String reason) {
        super(Instant.now(), HttpStatus.NOT_FOUND.value(), reason);
    }
}
