package com.missio.worship.missioworshipbackend.libs.authentication;

import java.util.List;

public class MissioValidationResponseSampler {

    public static MissioValidationResponse sample() {
        return MissioValidationResponse.builder()
                .isValid(true)
                .id(1234)
                .email("aaron@mbotton.com")
                .profilePicUrl("http://url.com")
                .name("Aaron")
                .roles(List.of("Role1, Role2"))
                .clearanceLevel(2)
                .build();
    }
}