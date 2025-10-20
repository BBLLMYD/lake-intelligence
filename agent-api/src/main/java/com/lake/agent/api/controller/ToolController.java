package com.lake.agent.api.controller;

import com.lake.agent.core.context.AgentContext;
import com.lake.agent.tools.Tool;
import com.lake.agent.tools.ToolInput;
import com.lake.agent.tools.ToolResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工具管理控制器
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tools")
@Tag(name = "Tool API", description = "工具管理和执行API")
public class ToolController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    @Operation(summary = "获取所有工具", description = "返回系统中所有可用的工具列表")
    public ResponseEntity<List<ToolInfo>> getAllTools() {
        Map<String, Tool> tools = applicationContext.getBeansOfType(Tool.class);

        List<ToolInfo> toolInfos = tools.values().stream()
                .map(tool -> ToolInfo.builder()
                        .name(tool.getName())
                        .description(tool.getDescription())
                        .version(tool.getVersion())
                        .available(tool.isAvailable())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(toolInfos);
    }

    @GetMapping("/{toolName}")
    @Operation(summary = "获取工具信息", description = "根据工具名称获取详细信息")
    public ResponseEntity<ToolInfo> getToolInfo(
            @Parameter(description = "工具名称") @PathVariable String toolName) {

        Map<String, Tool> tools = applicationContext.getBeansOfType(Tool.class);

        Tool tool = tools.values().stream()
                .filter(t -> toolName.equals(t.getName()))
                .findFirst()
                .orElse(null);

        if (tool == null) {
            return ResponseEntity.notFound().build();
        }

        ToolInfo toolInfo = ToolInfo.builder()
                .name(tool.getName())
                .description(tool.getDescription())
                .version(tool.getVersion())
                .available(tool.isAvailable())
                .build();

        return ResponseEntity.ok(toolInfo);
    }

    @PostMapping("/{toolName}/execute")
    @Operation(summary = "执行工具", description = "执行指定的工具")
    public ResponseEntity<ToolResult> executeTool(
            @Parameter(description = "工具名称") @PathVariable String toolName,
            @RequestBody ToolExecuteRequest request) {

        Map<String, Tool> tools = applicationContext.getBeansOfType(Tool.class);

        Tool tool = tools.values().stream()
                .filter(t -> toolName.equals(t.getName()))
                .findFirst()
                .orElse(null);

        if (tool == null) {
            return ResponseEntity.notFound().build();
        }

        if (!tool.isAvailable()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ToolInput input = ToolInput.builder()
                    .toolName(toolName)
                    .parameters(request.getParameters())
                    .build();

            AgentContext context = AgentContext.builder()
                    .sessionId(request.getSessionId())
                    .userId(request.getUserId())
                    .build();

            ToolResult result = tool.execute(input, context);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("Error executing tool: {}", toolName, e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 工具信息DTO
     */
    public static class ToolInfo {
        private String name;
        private String description;
        private String version;
        private boolean available;

        public static ToolInfoBuilder builder() {
            return new ToolInfoBuilder();
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }

        public static class ToolInfoBuilder {
            private String name;
            private String description;
            private String version;
            private boolean available;

            public ToolInfoBuilder name(String name) { this.name = name; return this; }
            public ToolInfoBuilder description(String description) { this.description = description; return this; }
            public ToolInfoBuilder version(String version) { this.version = version; return this; }
            public ToolInfoBuilder available(boolean available) { this.available = available; return this; }

            public ToolInfo build() {
                ToolInfo toolInfo = new ToolInfo();
                toolInfo.name = this.name;
                toolInfo.description = this.description;
                toolInfo.version = this.version;
                toolInfo.available = this.available;
                return toolInfo;
            }
        }
    }

    /**
     * 工具执行请求DTO
     */
    public static class ToolExecuteRequest {
        private Map<String, Object> parameters;
        private String userId;
        private String sessionId;

        // Getters and Setters
        public Map<String, Object> getParameters() { return parameters; }
        public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }
}