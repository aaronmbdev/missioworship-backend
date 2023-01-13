package com.missio.worship.missioworshipbackend.libs.authentication;

import lombok.Builder;

@Builder
public class ValidationResponse {
    private boolean isValid;
    private String email;
    private String pictureUrl;
}
