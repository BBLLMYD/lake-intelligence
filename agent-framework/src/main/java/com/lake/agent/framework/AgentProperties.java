package com.lake.agent.framework;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Agent配置属性
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "lake.agent")
public class AgentProperties {

    /**
     * 是否启用Agent框架
     */
    private boolean enabled = true;

    /**
     * Agent管理器配置
     */
    private Manager manager = new Manager();

    /**
     * AI模型配置
     */
    private Ai ai = new Ai();

    /**
     * 工具配置
     */
    private Tools tools = new Tools();

    /**
     * 扩展属性
     */
    private Map<String, Object> properties = new HashMap<>();

    @Data
    public static class Manager {
        /**
         * 最大Agent数量
         */
        private int maxAgents = 100;

        /**
         * Agent超时时间（毫秒）
         */
        private long timeout = 30000;

        /**
         * 是否启用Agent监控
         */
        private boolean monitoring = true;
    }

    @Data
    public static class Ai {
        /**
         * 默认AI模型提供商
         */
        private String provider = "openai";

        /**
         * 模型配置
         */
        private Map<String, Object> models = new HashMap<>();

        /**
         * API配置
         */
        private Map<String, String> apiConfig = new HashMap<>();
    }

    @Data
    public static class Tools {
        /**
         * 是否启用工具功能
         */
        private boolean enabled = true;

        /**
         * 工具扫描包路径
         */
        private String[] scanPackages = {"com.lake.agent.tools"};

        /**
         * 工具配置
         */
        private Map<String, Object> config = new HashMap<>();
    }
}