package com.missio.worship.missioworshipbackend.libs.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoogleTokenValidatorTest {
    @Mock
    GoogleIdTokenVerifier verifier;

    @InjectMocks
    GoogleTokenValidator customValidator;

    @Test
    void tokenVerificationReturnsEmailAndProfilePic() throws GeneralSecurityException, IOException {
        val randomToken = UUID.randomUUID().toString();
        val googleResponse = GoogleIdTokenSampler.sample();
        val email = googleResponse.getPayload().getEmail();
        val picture = googleResponse.getPayload().getOrDefault("picture", "").toString();

        when(verifier.verify(randomToken)).thenReturn(googleResponse);
        val expected = GoogleValidationResponse.builder()
                .email(email)
                .pictureUrl(picture)
                .isValid(true)
                .build();
        val result = customValidator.validateToken(randomToken);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void nullResponseFromVerifierReturnsEmptyUnvalidResponse() throws GeneralSecurityException, IOException {
        val randomToken = UUID.randomUUID().toString();
        when(verifier.verify(randomToken)).thenReturn(null);
        val expected = GoogleValidationResponse.builder()
                .email("")
                .pictureUrl("")
                .isValid(false)
                .build();
        val result = customValidator.validateToken(randomToken);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void verificationFailsOnVerifierException() throws GeneralSecurityException, IOException {
        val randomToken = UUID.randomUUID().toString();
        when(verifier.verify(randomToken)).thenThrow(new IOException("Error"));
        val expected = GoogleValidationResponse.builder()
                .email("")
                .pictureUrl("")
                .isValid(false)
                .build();
        val result = customValidator.validateToken(randomToken);
        assertThat(result).isEqualTo(expected);
    }
}