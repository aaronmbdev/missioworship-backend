package com.missio.worship.missioworshipbackend.libs.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
@AllArgsConstructor
public class GoogleTokenValidator {
    private final GoogleIdTokenVerifier verifier;

    /**
     * Method that validates a given Google Token
     * @param tokenToValidate
     * @return a Validation response. If isValid is true, contains a valid email and profilePicUrl. If not, both fields will be empty
     * Ih the account doesn't have a picture, an empty string will be returned
     */
    public GoogleValidationResponse validateToken(final String tokenToValidate) {
        val emptyValidationResponse = GoogleValidationResponse.builder().isValid(false).email("").pictureUrl("").build();
        try {
            var idToken = verifier.verify(tokenToValidate);
            if(idToken == null) return emptyValidationResponse;

            var payload = idToken.getPayload();
            return GoogleValidationResponse.builder()
                    .isValid(true)
                    .pictureUrl((String) payload.getOrDefault("picture", ""))
                    .email(payload.getEmail())
                    .build();

        } catch (IllegalArgumentException | GeneralSecurityException | IOException e) {
            log.info("Se ha intentado verificar un token que no es válido: {}", tokenToValidate, e);
            return emptyValidationResponse;
        }
    }
}
