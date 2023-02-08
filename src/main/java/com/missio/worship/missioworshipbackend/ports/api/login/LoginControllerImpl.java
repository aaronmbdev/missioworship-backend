package com.missio.worship.missioworshipbackend.ports.api.login;

import com.missio.worship.missioworshipbackend.libs.authentication.AuthenticationService;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.EmailNotFound;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/login/")
@RestController
public class LoginControllerImpl implements LoginController {

    private final AuthenticationService service;

    public LoginControllerImpl(AuthenticationService service) {
        this.service = service;
    }

    @Override
    public Mono<ResponseEntity<Object>> loginAttempt(final TokenInput input) {
        try {
            val token = service.validateTokenAndLogin(input.token());
            return Mono.just(ResponseEntity.ok(token));

        } catch (EmailNotFound | InvalidProvidedToken e) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(new BadRequestResponse(e.getMessage())));
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> renewToken(final TokenInput input) {
        try {
            val token = service.validateTokenAndRenew(input.token());
            return Mono.just(ResponseEntity.ok(token));

        } catch (InvalidProvidedToken | EmailNotFound e) {
            return Mono.just(ResponseEntity.badRequest()
                    .body(new BadRequestResponse(e.getMessage())));
        }
    }
}
