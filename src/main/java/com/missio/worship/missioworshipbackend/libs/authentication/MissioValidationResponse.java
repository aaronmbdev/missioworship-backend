package com.missio.worship.missioworshipbackend.libs.authentication;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MissioValidationResponse {

    private boolean isValid;
    private Integer id;
    private String name;
    private String email;
    private String profilePicUrl;
    private Integer clearanceLevel;
    private List<String> roles;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if(obj.getClass() != getClass()) return false;
        MissioValidationResponse newResponse = (MissioValidationResponse) obj;
        return isValid == newResponse.isValid &&
                name.equals(newResponse.name) &&
                email.equals(newResponse.email) &&
                clearanceLevel.equals(newResponse.clearanceLevel) &&
                profilePicUrl.equals(newResponse.profilePicUrl);
    }
}
