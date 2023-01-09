package com.missio.worship.missioworshipbackend.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenApi() {
        return new OpenAPI()
                .info(new Info().title("Missio Worship API")
                        .description("Documentación de la API de Missio Worship")
                        .version("v1.0.0")
                        .license(new License().name("Licenciado por Jesús")));
    }
}
