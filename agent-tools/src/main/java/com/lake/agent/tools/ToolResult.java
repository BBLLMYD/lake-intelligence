package com.lake.agent.tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 工具执行结果
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResult {

    /**
     * 执行是否成功
     */
    private boolean success;

    /**
     * 结果数据
     */
    private Object data;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 执行时间戳
     */
    private LocalDateTime timestamp;

    /**
     * 执行时长（毫秒）
     */
    private long executionTime;

    /**
     * 扩展信息
     */
    private Map<String, Object> metadata;

    /**
     * 创建成功结果
     *
     * @param data 结果数据
     * @return 成功结果
     */
    public static ToolResult success(Object data) {
        return ToolResult.builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 创建失败结果
     *
     * @param errorMessage 错误信息
     * @return 失败结果
     */
    public static ToolResult failure(String errorMessage) {
        return ToolResult.builder()
                .success(false)
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 创建失败结果
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误信息
     * @return 失败结果
     */
    public static ToolResult failure(String errorCode, String errorMessage) {
        return ToolResult.builder()
                .success(false)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }
}