package com.missio.worship.missioworshipbackend.ports.api.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/login/")
@RestController
public class LoginControllerImpl implements LoginController{
    @Override
    public Mono<ResponseEntity<String>> loginAttempt(final TokenInput input) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<String>> renewToken(final TokenInput input) {
        return null;
    }
}
