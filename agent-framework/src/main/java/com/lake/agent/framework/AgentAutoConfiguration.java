package com.lake.agent.framework;

import com.lake.agent.core.Agent;
import com.lake.agent.core.AgentManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Map;

/**
 * Agent自动配置类
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(AgentProperties.class)
@ConditionalOnProperty(prefix = "lake.agent", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = {"com.lake.agent"})
public class AgentAutoConfiguration {

    private final AgentProperties agentProperties;

    public AgentAutoConfiguration(AgentProperties agentProperties) {
        this.agentProperties = agentProperties;
        log.info("Agent framework auto configuration initialized");
    }

    /**
     * 创建默认的Agent管理器
     */
    @Bean
    @ConditionalOnMissingBean
    public AgentManager agentManager(ApplicationContext applicationContext) {
        log.info("Creating default AgentManager");
        DefaultAgentManager manager = new DefaultAgentManager(agentProperties);

        // 自动注册所有Agent Bean
        Map<String, Agent> agents = applicationContext.getBeansOfType(Agent.class);
        agents.values().forEach(agent -> {
            log.info("Auto-registering agent: {}", agent.getId());
            manager.registerAgent(agent);
        });

        return manager;
    }
}