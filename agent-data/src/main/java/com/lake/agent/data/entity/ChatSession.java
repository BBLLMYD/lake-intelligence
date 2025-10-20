package com.lake.agent.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 聊天会话实体
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("chat_session")
public class ChatSession {

    /**
     * 会话ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 会话标题
     */
    @NotBlank(message = "会话标题不能为空")
    private String title;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * Agent ID
     */
    @NotBlank(message = "Agent ID不能为空")
    private String agentId;

    /**
     * 会话状态：ACTIVE, INACTIVE, ARCHIVED
     */
    @NotNull(message = "会话状态不能为空")
    private String status;

    /**
     * 会话配置JSON
     */
    private String configuration;

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

    /**
     * 最后活跃时间
     */
    private LocalDateTime lastActiveTime;
}