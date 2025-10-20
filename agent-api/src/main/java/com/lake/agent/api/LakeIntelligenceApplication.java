package com.lake.agent.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Lake Intelligence AI Agent 框架主启动类
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.lake.agent")
public class LakeIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LakeIntelligenceApplication.class, args);
    }
}