package com.lake.agent.core.message;

/**
 * 消息类型枚举
 * 定义Agent间通信的消息类型
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
public enum MessageType {

    /**
     * 文本消息
     */
    TEXT,

    /**
     * 命令消息
     */
    COMMAND,

    /**
     * 查询消息
     */
    QUERY,

    /**
     * 响应消息
     */
    RESPONSE,

    /**
     * 通知消息
     */
    NOTIFICATION,

    /**
     * 错误消息
     */
    ERROR,

    /**
     * 系统消息
     */
    SYSTEM,

    /**
     * 工具调用消息
     */
    TOOL_CALL,

    /**
     * 工具响应消息
     */
    TOOL_RESPONSE,

    /**
     * 多媒体消息
     */
    MEDIA
}