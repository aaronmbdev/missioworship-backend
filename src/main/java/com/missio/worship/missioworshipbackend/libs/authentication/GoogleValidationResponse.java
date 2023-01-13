package com.missio.worship.missioworshipbackend.libs.authentication;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GoogleValidationResponse {
    private boolean isValid;
    private String email;
    private String pictureUrl;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(obj.getClass() != getClass()) return false;
        GoogleValidationResponse newResponse = (GoogleValidationResponse) obj;
        return isValid == newResponse.isValid &&
                email.equals(newResponse.email) &&
                pictureUrl.equals(newResponse.pictureUrl);
    }
}
