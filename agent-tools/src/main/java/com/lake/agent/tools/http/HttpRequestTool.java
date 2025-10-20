package com.lake.agent.tools.http;

import com.lake.agent.core.context.AgentContext;
import com.lake.agent.tools.Tool;
import com.lake.agent.tools.ToolInput;
import com.lake.agent.tools.ToolResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * HTTP请求工具
 * 支持发送HTTP请求
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@Component
public class HttpRequestTool implements Tool {

    private final WebClient webClient;

    public HttpRequestTool() {
        this.webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
                .build();
    }

    @Override
    public String getName() {
        return "http_request";
    }

    @Override
    public String getDescription() {
        return "Send HTTP requests to external services";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public ToolResult execute(ToolInput input, AgentContext context) {
        long startTime = System.currentTimeMillis();

        try {
            String url = input.getParameter("url");
            String method = input.getParameter("method", "GET");
            Object body = input.getParameter("body");
            Integer timeout = input.getParameter("timeout", 10);

            log.debug("Executing HTTP {} request to: {}", method, url);

            WebClient.RequestHeadersSpec<?> spec = switch (method.toUpperCase()) {
                case "GET" -> webClient.get().uri(url);
                case "POST" -> webClient.post().uri(url).bodyValue(body != null ? body : "");
                case "PUT" -> webClient.put().uri(url).bodyValue(body != null ? body : "");
                case "DELETE" -> webClient.delete().uri(url);
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            String response = spec
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(timeout))
                    .block();

            long executionTime = System.currentTimeMillis() - startTime;

            return ToolResult.builder()
                    .success(true)
                    .data(response)
                    .executionTime(executionTime)
                    .build();

        } catch (Exception e) {
            log.error("HTTP request failed", e);
            long executionTime = System.currentTimeMillis() - startTime;

            return ToolResult.builder()
                    .success(false)
                    .errorMessage("HTTP request failed: " + e.getMessage())
                    .executionTime(executionTime)
                    .build();
        }
    }

    @Override
    public boolean validate(ToolInput input) {
        String url = input.getParameter("url");
        if (!StringUtils.hasText(url)) {
            log.warn("URL parameter is required for HTTP request");
            return false;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            log.warn("Invalid URL format: {}", url);
            return false;
        }

        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}