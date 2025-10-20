package com.lake.agent.core.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Agent消息实体
 * 封装Agent间通信的消息内容
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    /**
     * 消息唯一标识
     */
    private String id;

    /**
     * 消息类型
     */
    private MessageType type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息发送者
     */
    private String sender;

    /**
     * 消息接收者
     */
    private String receiver;

    /**
     * 消息创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 扩展属性
     */
    private Map<String, Object> metadata;

    /**
     * 消息优先级
     */
    private Priority priority;

    /**
     * 消息状态
     */
    private MessageStatus status;

    /**
     * 消息优先级枚举
     */
    public enum Priority {
        LOW, NORMAL, HIGH, URGENT
    }

    /**
     * 消息状态枚举
     */
    public enum MessageStatus {
        CREATED, SENT, RECEIVED, PROCESSED, FAILED
    }
}