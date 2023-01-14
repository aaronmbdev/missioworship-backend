package com.missio.worship.missioworshipbackend.ports.api.common;

import com.missio.worship.missioworshipbackend.config.AppProperties;
import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponse;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorizationChecker {
    private final AuthTokenService tokenService;

    private final AppProperties properties;

    public void verifyTokenValidity(final String token) throws InvalidProvidedToken {
        doTokenVerification(token);
    }

    public boolean verifyTokenAndAdmin(String token) throws InvalidProvidedToken {
        val response = doTokenVerification(token);
        return response.getRoles().contains(this.properties.getAdminRole());
    }

    private MissioValidationResponse doTokenVerification(String token) throws InvalidProvidedToken {
        val cleanToken = tokenService.extractTokenFromHeader(token);
        val tokenVerification = tokenService.verifyTokenValidity(cleanToken, new Date().toInstant());
        if(!tokenVerification.isValid()) {
            log.info("El token provisto no es válido. Se interrumpe ejecución.");
            throw new InvalidProvidedToken();
        }
        return tokenVerification;
    }
}
