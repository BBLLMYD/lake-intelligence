package com.lake.agent.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康检查控制器
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health API", description = "系统健康检查API")
public class HealthController {

    @GetMapping
    @Operation(summary = "健康检查", description = "检查系统运行状态")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("timestamp", LocalDateTime.now());
        healthInfo.put("service", "Lake Intelligence AI Agent Framework");
        healthInfo.put("version", "1.0.0-SNAPSHOT");

        return ResponseEntity.ok(healthInfo);
    }

    @GetMapping("/info")
    @Operation(summary = "系统信息", description = "获取系统详细信息")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> systemInfo = new HashMap<>();

        // 系统信息
        systemInfo.put("name", "Lake Intelligence AI Agent Framework");
        systemInfo.put("version", "1.0.0-SNAPSHOT");
        systemInfo.put("description", "基于Spring Boot的AI Agent开发框架");

        // 运行环境
        Map<String, Object> environment = new HashMap<>();
        environment.put("javaVersion", System.getProperty("java.version"));
        environment.put("osName", System.getProperty("os.name"));
        environment.put("osVersion", System.getProperty("os.version"));
        environment.put("architecture", System.getProperty("os.arch"));

        systemInfo.put("environment", environment);
        systemInfo.put("uptime", System.currentTimeMillis());

        return ResponseEntity.ok(systemInfo);
    }
}