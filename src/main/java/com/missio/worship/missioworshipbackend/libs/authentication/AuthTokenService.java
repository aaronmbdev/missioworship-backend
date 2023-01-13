package com.missio.worship.missioworshipbackend.libs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.missio.worship.missioworshipbackend.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AuthTokenService {
    private final AppProperties properties;
    private final String issuer;
    private static final int ONE_DAY = 86400000;

    public AuthTokenService(AppProperties properties) {
        this.properties = properties;
        this.issuer = "MissioSantCugat";
    }

    public String issueToken(String name, String email, String profilePicUrl, List<String> roles) {
        return JWT.create()
                .withClaim("name", name)
                .withClaim("email", email)
                .withClaim("profilePicUrl", profilePicUrl)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withIssuer(this.issuer)
                .withExpiresAt(new Date(System.currentTimeMillis() + ONE_DAY))
                .sign(Algorithm.HMAC256(this.properties.getJwt().getSecret()));
    }

    public MissioValidationResponse verifyTokenValidity(String token, Instant now) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.properties.getJwt().getSecret())).withIssuer(this.issuer).build();
        var emptyValidationResponse = MissioValidationResponse.builder().build();
        try {
            var decoded = verifier.verify(token);
            log.trace("Decoded token: {}", decoded);
            var expiry = decoded.getExpiresAt().toInstant();
            if(expiry.isBefore(now)) {
                return emptyValidationResponse;
            }
            return MissioValidationResponse.builder()
                    .isValid(true)
                    .email(decoded.getClaim("email").asString())
                    .profilePicUrl(decoded.getClaim("profilePicUrl").asString())
                    .name(decoded.getClaim("name").asString())
                    .roles(decoded.getClaim("roles").asList(String.class))
                    .build();

        } catch (JWTVerificationException e) {
            log.info("El token no ha podido validarse. Parece que no es correcto. ", e);
            return emptyValidationResponse;
        }
    }

    public String extractTokenFromHeader(String dirtyToken) {
        return dirtyToken.replace("Bearer ", "");
    }

}
