package com.missio.worship.missioworshipbackend.libs.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.webtoken.JsonWebSignature;

public class GoogleIdTokenSampler {
    public static GoogleIdToken sample() {
        var headerSample = headerSampler();
        var payload = payloadSampler();
        return new GoogleIdToken(headerSample, payload, new byte[]{}, new byte[]{});
    }
    private static JsonWebSignature.Header headerSampler() {
        return new JsonWebSignature.Header();
    }

    private static GoogleIdToken.Payload payloadSampler() {
        var sample = new GoogleIdToken.Payload();
        sample.set("picture","aValidUrlPicture");
        sample.setEmail("thisIsAnEmail@server.com");
        return sample;
    }
}
