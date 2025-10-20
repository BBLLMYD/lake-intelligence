package com.lake.agent.core;

import com.lake.agent.core.context.AgentContext;
import com.lake.agent.core.message.Message;
import com.lake.agent.core.message.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Agent抽象基类
 * 提供Agent的基础实现
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractAgent implements Agent {

    /**
     * Agent ID
     */
    protected String id;

    /**
     * Agent名称
     */
    protected String name;

    /**
     * Agent描述
     */
    protected String description;

    /**
     * Agent状态
     */
    protected volatile boolean available = false;

    /**
     * 构造函数
     */
    protected AbstractAgent() {
        this.id = generateId();
    }

    /**
     * 构造函数
     *
     * @param name        Agent名称
     * @param description Agent描述
     */
    protected AbstractAgent(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Message process(Message message, AgentContext context) {
        if (!isAvailable()) {
            log.warn("Agent {} is not available", getId());
            return createErrorMessage("Agent is not available", message);
        }

        try {
            log.debug("Processing message: {} for agent: {}", message.getId(), getId());
            return doProcess(message, context);
        } catch (Exception e) {
            log.error("Error processing message {} for agent {}", message.getId(), getId(), e);
            return createErrorMessage("Error processing message: " + e.getMessage(), message);
        }
    }

    /**
     * 具体的消息处理逻辑，由子类实现
     *
     * @param message 输入消息
     * @param context Agent执行上下文
     * @return 处理结果消息
     */
    protected abstract Message doProcess(Message message, AgentContext context);

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void initialize() {
        log.info("Initializing agent: {}", getId());
        doInitialize();
        this.available = true;
        log.info("Agent {} initialized successfully", getId());
    }

    @Override
    public void destroy() {
        log.info("Destroying agent: {}", getId());
        this.available = false;
        doDestroy();
        log.info("Agent {} destroyed successfully", getId());
    }

    /**
     * 具体的初始化逻辑，由子类实现
     */
    protected void doInitialize() {
        // 默认实现为空
    }

    /**
     * 具体的销毁逻辑，由子类实现
     */
    protected void doDestroy() {
        // 默认实现为空
    }

    /**
     * 生成Agent ID
     *
     * @return Agent ID
     */
    protected String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 创建错误消息
     *
     * @param errorMsg      错误信息
     * @param originalMessage 原始消息
     * @return 错误消息
     */
    protected Message createErrorMessage(String errorMsg, Message originalMessage) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .type(MessageType.ERROR)
                .content(errorMsg)
                .sender(getId())
                .receiver(originalMessage.getSender())
                .timestamp(LocalDateTime.now())
                .status(Message.MessageStatus.CREATED)
                .priority(Message.Priority.HIGH)
                .build();
    }

    /**
     * 创建响应消息
     *
     * @param content         响应内容
     * @param originalMessage 原始消息
     * @return 响应消息
     */
    protected Message createResponseMessage(String content, Message originalMessage) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .type(MessageType.RESPONSE)
                .content(content)
                .sender(getId())
                .receiver(originalMessage.getSender())
                .timestamp(LocalDateTime.now())
                .status(Message.MessageStatus.CREATED)
                .priority(originalMessage.getPriority())
                .build();
    }
}