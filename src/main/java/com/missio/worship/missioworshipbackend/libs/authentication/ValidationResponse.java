package com.missio.worship.missioworshipbackend.libs.authentication;

import lombok.Builder;

@Builder
public class ValidationResponse {
    private boolean isValid;
    private String email;
    private String pictureUrl;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(obj.getClass() != getClass()) return false;
        ValidationResponse newResponse = (ValidationResponse) obj;
        return isValid == newResponse.isValid &&
                email.equals(newResponse.email) &&
                pictureUrl.equals(newResponse.pictureUrl);
    }
}
