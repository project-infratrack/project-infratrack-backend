package com.G153.InfratrackUserPortal.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI infratrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Infratrack User Portal API")
                        .description("API documentation for Infratrack User Portal")
                        .version("v1.0.0")
                        .license(new License().name("MIT License")));
    }
}