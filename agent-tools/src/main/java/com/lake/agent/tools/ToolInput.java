package com.lake.agent.tools;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 工具输入参数
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolInput {

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * 输入参数
     */
    private Map<String, Object> parameters;

    /**
     * 获取参数值
     *
     * @param key 参数键
     * @param <T> 参数值类型
     * @return 参数值
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key) {
        return parameters != null ? (T) parameters.get(key) : null;
    }

    /**
     * 获取参数值，如果不存在则返回默认值
     *
     * @param key          参数键
     * @param defaultValue 默认值
     * @param <T>          参数值类型
     * @return 参数值或默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key, T defaultValue) {
        T value = getParameter(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 检查是否包含参数
     *
     * @param key 参数键
     * @return true如果包含，否则false
     */
    public boolean hasParameter(String key) {
        return parameters != null && parameters.containsKey(key);
    }
}