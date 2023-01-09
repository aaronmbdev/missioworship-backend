package com.missio.worship.missioworshipbackend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CorsFilter {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //TODO: Modificar esto por seguridad
                registry.addMapping("/**")
                        .allowedOrigins(
                                "*"
                        )
                        .allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
            }
        };
    }

}