package com.missio.worship.missioworshipbackend.config.captcha;

import com.missio.worship.missioworshipbackend.config.AppProperties;
import com.missio.worship.missioworshipbackend.config.ConfigValidator;
import com.missio.worship.missioworshipbackend.libs.errors.BootingError;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecaptchaConfig {
    @Autowired
    AppProperties properties;

    @PostConstruct
    public void validateConfigOrDie() throws BootingError {
        var secret = properties.getCaptcha().getSecret();
        ConfigValidator.configExistsValidator(secret);
    }
}