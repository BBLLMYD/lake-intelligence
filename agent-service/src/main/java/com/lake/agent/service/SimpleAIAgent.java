package com.lake.agent.service;

import com.lake.agent.core.AbstractAgent;
import com.lake.agent.core.context.AgentContext;
import com.lake.agent.core.message.Message;
import com.lake.agent.core.message.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 简单AI Agent实现
 * 提供基本的对话功能
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@Component
public class SimpleAIAgent extends AbstractAgent {

    public SimpleAIAgent() {
        super("Simple AI Agent", "A basic AI agent for demonstration");
    }

    @Override
    protected Message doProcess(Message message, AgentContext context) {
        log.info("SimpleAIAgent processing message: {}", message.getContent());

        // 简单的回复逻辑
        String response = switch (message.getType()) {
            case TEXT -> processTextMessage(message.getContent());
            case QUERY -> processQueryMessage(message.getContent());
            case COMMAND -> processCommandMessage(message.getContent());
            default -> "I can only process text, query, and command messages.";
        };

        return Message.builder()
                .id(UUID.randomUUID().toString())
                .type(MessageType.RESPONSE)
                .content(response)
                .sender(getId())
                .receiver(message.getSender())
                .timestamp(LocalDateTime.now())
                .status(Message.MessageStatus.CREATED)
                .priority(message.getPriority())
                .build();
    }

    private String processTextMessage(String content) {
        if (content.toLowerCase().contains("hello") || content.toLowerCase().contains("hi")) {
            return "Hello! I'm your AI assistant. How can I help you today?";
        } else if (content.toLowerCase().contains("help")) {
            return "I can help you with various tasks. You can ask me questions, give me commands, or just have a conversation.";
        } else if (content.toLowerCase().contains("weather")) {
            return "I don't have access to real-time weather data, but you can check your local weather service for accurate information.";
        } else {
            return "Thank you for your message: \"" + content + "\". I'm a simple AI agent and I'm here to help!";
        }
    }

    private String processQueryMessage(String content) {
        return "I received your query: \"" + content + "\". While I'm a simple agent, I'll do my best to provide helpful information.";
    }

    private String processCommandMessage(String content) {
        if (content.toLowerCase().contains("status")) {
            return "Agent Status: Online and ready to help!";
        } else if (content.toLowerCase().contains("time")) {
            return "Current time: " + LocalDateTime.now();
        } else {
            return "Command received: \"" + content + "\". This is a simple agent with limited command capabilities.";
        }
    }

    @Override
    protected void doInitialize() {
        log.info("SimpleAIAgent initialized successfully");
    }

    @Override
    protected void doDestroy() {
        log.info("SimpleAIAgent destroyed");
    }
}