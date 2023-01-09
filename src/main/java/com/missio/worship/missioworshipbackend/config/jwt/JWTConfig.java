package com.missio.worship.missioworshipbackend.config.jwt;

import com.missio.worship.missioworshipbackend.config.AppProperties;
import com.missio.worship.missioworshipbackend.config.ConfigValidator;
import com.missio.worship.missioworshipbackend.libs.errors.BootingError;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Autowired
    AppProperties appProperties;

    @PostConstruct
    public void testConfigurationOrDie() throws BootingError {
        var config = appProperties.getJwt().getSecret();
        ConfigValidator.configExistsValidator(config);
    }
}