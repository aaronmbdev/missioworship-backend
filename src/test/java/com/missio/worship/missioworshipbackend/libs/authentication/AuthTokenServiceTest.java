package com.missio.worship.missioworshipbackend.libs.authentication;

import com.missio.worship.missioworshipbackend.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthTokenServiceTest {

    @Mock
    AppProperties properties;

    @InjectMocks
    AuthTokenService service;

    @Test
    void tokenIssueTest() {
        var jwt = new AppProperties.JWTConfig();
        jwt.setSecret("ABC1234");

        when(properties.getJwt()).thenReturn(jwt);

        generateValidToken();
        /*
        Silly test but nothing should happen
         */
    }

    @Test
    void invalidTokenTest() {
        val token = "randomUnvalidToken";
        var jwt = new AppProperties.JWTConfig();
        jwt.setSecret("ABC1234");

        when(properties.getJwt()).thenReturn(jwt);

        val result = service.verifyTokenValidity(token, new Date().toInstant());
        assertThat(result.isValid()).isFalse();
    }

    @Test
    void expiredTokenTest() {
        var now = new Date().toInstant();
        var futureTravel = now.plus(3, ChronoUnit.DAYS);
        var jwt = new AppProperties.JWTConfig();
        jwt.setSecret("ABC1234");

        when(properties.getJwt()).thenReturn(jwt);

        val token = generateValidToken();
        val valid = service.verifyTokenValidity(token, futureTravel);
        assertThat(valid.isValid()).isFalse();
    }

    @Test
    void validTokenVerificationTest() {
        val name = "aaron";
        val email = "test@mail.com";
        val url = "http://noexistsx.com";
        val roles = List.of("One", "Two");
        var jwt = new AppProperties.JWTConfig();
        jwt.setSecret("ABC1234");
        when(properties.getJwt()).thenReturn(jwt);

        val token = service.issueToken(name, email, url, roles);
        val result = service.verifyTokenValidity(token, new Date().toInstant());
        assertThat(result.isValid()).isTrue();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getProfilePicUrl()).isEqualTo(url);
        assertThat(result.getRoles()).isEqualTo(roles);
    }

    private String generateValidToken() {
        val token = service.issueToken("aaron", "test@email.com", "http://test.com", List.of("One", "Two"));
        log.info("TOKEN REQUESTED: {}", token);
        return token;
    }
}