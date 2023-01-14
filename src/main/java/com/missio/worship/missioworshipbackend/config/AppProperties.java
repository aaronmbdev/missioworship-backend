package com.missio.worship.missioworshipbackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "missio")
@Getter
@Setter
public class AppProperties {
    private CaptchaConfig captcha;
    private JWTConfig jwt;

    private String adminRole;

    @Getter
    @Setter
    public static class CaptchaConfig {
        String secret;
    }

    @Getter
    @Setter
    public static class JWTConfig {
        String secret;
    }
}
