package com.missio.worship.missioworshipbackend.libs.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class GoogleTokenValidator {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenValidator(GoogleIdTokenVerifier verifier) {
        this.verifier = verifier;
    }

    public ValidationResponse validateToken(final String tokenToValidate) {
        try {
            var idToken = verifier.verify(tokenToValidate);
            if(idToken == null) return ValidationResponse.builder().build();

            return ValidationResponse.builder()
                    .isValid(true)
                    .pictureUrl("")
                    .email("")
                    .build();

        } catch (GeneralSecurityException | IOException e) {
            log.info("Se ha intentado verificar un token que no es válido: {}", tokenToValidate, e);
            return ValidationResponse.builder().build();
        }
    }
}
