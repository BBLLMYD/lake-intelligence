package com.lake.agent.api.controller;

import com.lake.agent.core.Agent;
import com.lake.agent.core.context.AgentContext;
import com.lake.agent.core.message.Message;
import com.lake.agent.core.message.MessageType;
import com.lake.agent.service.AgentService;
import com.lake.agent.service.AgentStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Agent REST API控制器
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/agents")
@Tag(name = "Agent API", description = "AI Agent管理和交互API")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping
    @Operation(summary = "获取所有可用的Agent", description = "返回系统中所有可用的Agent列表")
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.getAvailableAgents();
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/{agentId}")
    @Operation(summary = "获取Agent信息", description = "根据Agent ID获取详细信息")
    public ResponseEntity<Agent> getAgent(
            @Parameter(description = "Agent ID") @PathVariable String agentId) {
        return agentService.getAgentInfo(agentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "搜索Agent", description = "根据名称搜索Agent")
    public ResponseEntity<List<Agent>> searchAgents(
            @Parameter(description = "Agent名称") @RequestParam String name) {
        List<Agent> agents = agentService.findAgentsByName(name);
        return ResponseEntity.ok(agents);
    }

    @PostMapping("/{agentId}/chat")
    @Operation(summary = "与Agent对话", description = "发送消息给指定的Agent")
    public ResponseEntity<Message> chatWithAgent(
            @Parameter(description = "Agent ID") @PathVariable String agentId,
            @RequestBody ChatRequest request) {

        // 构建消息
        Message message = Message.builder()
                .id(UUID.randomUUID().toString())
                .type(MessageType.TEXT)
                .content(request.getMessage())
                .sender(request.getUserId())
                .receiver(agentId)
                .timestamp(LocalDateTime.now())
                .status(Message.MessageStatus.CREATED)
                .priority(Message.Priority.NORMAL)
                .build();

        // 构建上下文
        AgentContext context = AgentContext.builder()
                .sessionId(request.getSessionId())
                .userId(request.getUserId())
                .agentId(agentId)
                .build();

        try {
            Message response = agentService.processMessage(agentId, message, context);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing message for agent: {}", agentId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/statistics")
    @Operation(summary = "获取Agent统计信息", description = "获取系统中Agent的统计数据")
    public ResponseEntity<AgentStatistics> getStatistics() {
        AgentStatistics statistics = agentService.getStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * 聊天请求DTO
     */
    public static class ChatRequest {
        private String message;
        private String userId;
        private String sessionId;

        // Getters and Setters
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }
}