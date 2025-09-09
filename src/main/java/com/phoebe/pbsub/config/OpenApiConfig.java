package com.phoebe.pbsub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) Configuration
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PB Client Reporting Subscription API")
                        .description("PB Client Report Subscription System API Documentation - Complete CRUD operations for client and subscription management")
                        .version("1.0.0"));
    }
}
