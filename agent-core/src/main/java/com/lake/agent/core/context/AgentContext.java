package com.lake.agent.core.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Agent执行上下文
 * 维护Agent执行过程中的状态和信息
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentContext {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * Agent ID
     */
    private String agentId;

    /**
     * 上下文属性
     */
    @Builder.Default
    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    /**
     * 会话历史
     */
    @Builder.Default
    private Map<String, Object> sessionHistory = new ConcurrentHashMap<>();

    /**
     * 配置信息
     */
    @Builder.Default
    private Map<String, Object> configuration = new ConcurrentHashMap<>();

    /**
     * 获取属性值
     *
     * @param key 属性键
     * @param <T> 属性值类型
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }

    /**
     * 设置属性值
     *
     * @param key   属性键
     * @param value 属性值
     */
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * 移除属性
     *
     * @param key 属性键
     * @return 被移除的属性值
     */
    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    /**
     * 检查是否包含属性
     *
     * @param key 属性键
     * @return true如果包含，否则false
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    /**
     * 获取配置值
     *
     * @param key 配置键
     * @param <T> 配置值类型
     * @return 配置值
     */
    @SuppressWarnings("unchecked")
    public <T> T getConfig(String key) {
        return (T) configuration.get(key);
    }

    /**
     * 设置配置值
     *
     * @param key   配置键
     * @param value 配置值
     */
    public void setConfig(String key, Object value) {
        configuration.put(key, value);
    }

    /**
     * 清空上下文
     */
    public void clear() {
        attributes.clear();
        sessionHistory.clear();
        configuration.clear();
    }
}