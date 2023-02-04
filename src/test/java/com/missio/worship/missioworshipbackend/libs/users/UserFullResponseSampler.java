package com.missio.worship.missioworshipbackend.libs.users;

import java.util.List;

class UserFullResponseSampler {
    public static UserFullResponse sample() {
        return UserFullResponse.builder()
                .id(1)
                .name("A user")
                .email("email@domain.com")
                .roles(List.of("Test", "Test2"))
                .build();
    }
}