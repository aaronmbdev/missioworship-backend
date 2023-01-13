package com.missio.worship.missioworshipbackend.libs.authentication;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.EmailNotFound;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {
    private final GoogleTokenValidator googleTokenValidator;

    private final AuthTokenService tokenIssuingService;

    private final UserRepository userRepository;

    public AuthenticationService(GoogleTokenValidator googleTokenValidator, AuthTokenService tokenIssuingService, UserRepository userRepository) {
        this.googleTokenValidator = googleTokenValidator;
        this.tokenIssuingService = tokenIssuingService;
        this.userRepository = userRepository;
    }

    public String validateTokenAndLogin(final String inputToken) throws InvalidProvidedToken, EmailNotFound {
        val googleResponse = googleTokenValidator.validateToken(inputToken);
        if(!googleResponse.isValid()) {
            throw new InvalidProvidedToken("El token suministrado no es válido");
        }
        val email = googleResponse.getEmail();
        val profile = googleResponse.getPictureUrl();
        val user = userRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFound("El correo "+ email +" no está registrado en el sistema. Login no permitido")
        );

        return tokenIssuingService.issueToken(
                user.getName(),
                user.getEmail(),
                profile,
                user.getRoles().stream().map(Role::getName).toList()
        );

    }

    public String validateTokenAndRenew(final String ourToken) throws InvalidProvidedToken {
        val tokenInformation = tokenIssuingService.verifyTokenValidity(ourToken, new Date().toInstant());
        if(!tokenInformation.isValid()) {
            throw new InvalidProvidedToken("El token ya no es válido. Probablemente haya expirado.");
        }
        return tokenIssuingService.issueToken(
                tokenInformation.getName(),
                tokenInformation.getEmail(),
                tokenInformation.getProfilePicUrl(),
                tokenInformation.getRoles());
    }


}
