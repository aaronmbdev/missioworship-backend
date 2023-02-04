package com.missio.worship.missioworshipbackend.ports.api.common;

import com.missio.worship.missioworshipbackend.config.AppProperties;
import com.missio.worship.missioworshipbackend.libs.authentication.AuthTokenService;
import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponse;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorizationChecker {
    private final AuthTokenService tokenService;

    private final AppProperties properties;


    public boolean userIsAdminOrHimself(List<Role> roles, final User user, final MissioValidationResponse decodedToken) {
        val isAdmin = roles.stream()
                .anyMatch(role -> role.getName().equals(properties.getAdminRole()));
        val myself = decodedToken.getEmail().equals(user.getEmail());
        return isAdmin || myself;
    }

    public boolean verifyTokenAndAdmin(MissioValidationResponse decodedToken)  {
        return decodedToken.getRoles().contains(this.properties.getAdminRole());
    }

    public MissioValidationResponse doTokenVerification(String token) throws InvalidProvidedToken {
        if(token == null) {
            log.info("No se ha enviado el token de seguridad");
            throw new InvalidProvidedToken();
        }
        val cleanToken = tokenService.extractTokenFromHeader(token);
        val tokenVerification = tokenService.verifyTokenValidity(cleanToken, new Date().toInstant());
        if(!tokenVerification.isValid()) {
            log.info("El token provisto no es válido. Se interrumpe ejecución.");
            throw new InvalidProvidedToken();
        }
        return tokenVerification;
    }
}
