package com.lake.agent.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 聊天消息实体
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat_message")
public class ChatMessage {

    /**
     * 消息ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 会话ID
     */
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    /**
     * 消息类型：USER, AGENT, SYSTEM
     */
    @NotBlank(message = "消息类型不能为空")
    private String messageType;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 发送者名称
     */
    private String senderName;

    /**
     * 消息状态：SENT, DELIVERED, READ, FAILED
     */
    @NotNull(message = "消息状态不能为空")
    private String status;

    /**
     * 消息元数据JSON
     */
    private String metadata;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}