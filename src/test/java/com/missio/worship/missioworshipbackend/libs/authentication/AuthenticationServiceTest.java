package com.missio.worship.missioworshipbackend.libs.authentication;


import com.missio.worship.missioworshipbackend.libs.authentication.errors.EmailNotFound;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.users.RolesService;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.UserSampler;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;
import lombok.val;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    GoogleTokenValidator googleTokenValidator;

    @Mock
    AuthTokenService tokenIssuingService;

    @Mock
    UserRepository userRepository;

    @Mock
    RolesService rolesService;

    @InjectMocks
    AuthenticationService service;

    @Test
    void loginAttemptFailsIfInvalidToken() {
        val invalidToken = UUID.randomUUID().toString();
        when(googleTokenValidator.validateToken(invalidToken)).thenReturn(GoogleValidationResponse.builder().build());

        val exception = assertThrows(InvalidProvidedToken.class, () -> service.validateTokenAndLogin(invalidToken));
        assertThat(exception.getMessage()).isEqualTo("El token suministrado no es válido");
    }

    @Test
    void loginAttemptFailsIfUserDoesntExists() {
        val validToken = UUID.randomUUID().toString();
        val email = "myEmail@email.com";
        when(googleTokenValidator.validateToken(validToken)).thenReturn(GoogleValidationResponse.builder()
                        .pictureUrl("anUrl")
                        .email(email)
                        .isValid(true)
                .build());

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        val exception = assertThrows(EmailNotFound.class, () -> service.validateTokenAndLogin(validToken));
        assertThat(exception.getMessage()).isEqualTo("El correo myEmail@email.com no está registrado en el sistema. Login no permitido");
    }

    @Test
    void loginAttemptSuccessfulTokenIssued() throws EmailNotFound, InvalidProvidedToken {
        val validToken = UUID.randomUUID().toString();
        val responseToken = UUID.randomUUID().toString();
        val email = "myEmail@email.com";
        var userSample = UserSampler.sample();
        userSample.setEmail(email);
        when(googleTokenValidator.validateToken(validToken)).thenReturn(GoogleValidationResponse.builder()
                .pictureUrl("anUrl")
                .email(email)
                .isValid(true)
                .build());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userSample));
        when(tokenIssuingService.issueToken(any(), any(), any(), any(), any())).thenReturn(responseToken);
        val result = service.validateTokenAndLogin(validToken);
        assertThat(result).isEqualTo(responseToken);
    }

    @Test
    void renewAndInvalidToken() {
        val invalidToken = UUID.randomUUID().toString();
        when(tokenIssuingService.verifyTokenValidity(any(), any())).thenReturn(MissioValidationResponse.builder().build());

        val exception = assertThrows(InvalidProvidedToken.class, () -> service.validateTokenAndRenew(invalidToken));
        assertThat(exception.getMessage()).isEqualTo("El token ya no es válido. Probablemente haya expirado.");
    }
}