package com.lake.agent.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI配置类
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lake Intelligence AI Agent API")
                        .description("基于Spring Boot的AI Agent开发框架API文档")
                        .version("1.0.0-SNAPSHOT")
                        .contact(new Contact()
                                .name("Lake Intelligence Team")
                                .email("contact@lake-intelligence.com")
                                .url("https://github.com/lake-intelligence"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}