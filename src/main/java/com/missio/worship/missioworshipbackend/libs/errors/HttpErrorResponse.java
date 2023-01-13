package com.missio.worship.missioworshipbackend.libs.errors;

import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public abstract class HttpErrorResponse {
    public final Instant timestamp;
    public final int status;
    public final List<String> problems;

    protected HttpErrorResponse(Instant time, int code, List<String> errors) {
        timestamp = time;
        status = code;
        problems = errors;
    }

    protected HttpErrorResponse(Instant time, int code, String error) {
        this(time, code, Arrays.asList(error));
    }

    public ResponseEntity<Object> toObjectEntity() {
        return ResponseEntity.status(status).body(this);
    }
}
