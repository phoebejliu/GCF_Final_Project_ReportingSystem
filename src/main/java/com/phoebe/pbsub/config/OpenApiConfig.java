package com.phoebe.pbsub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) Configuration
 * OpenAPI (Swagger) 配置
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PB Client Reporting Subscription API")
                        .description("PB客户报告订阅系统API文档 - 包含客户管理和订阅管理的完整CRUD操作")
                        .version("1.0.0"));
    }
}
