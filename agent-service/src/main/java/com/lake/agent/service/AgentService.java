package com.lake.agent.service;

import com.lake.agent.core.Agent;
import com.lake.agent.core.AgentManager;
import com.lake.agent.core.context.AgentContext;
import com.lake.agent.core.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Agent业务服务类
 * 提供Agent相关的业务操作
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@Service
public class AgentService {

    @Autowired
    private AgentManager agentManager;

    /**
     * 处理Agent消息
     *
     * @param agentId Agent ID
     * @param message 消息
     * @param context 上下文
     * @return 处理结果
     */
    public Message processMessage(String agentId, Message message, AgentContext context) {
        log.info("Processing message for agent: {}", agentId);

        Optional<Agent> agentOpt = agentManager.getAgent(agentId);
        if (agentOpt.isEmpty()) {
            log.warn("Agent not found: {}", agentId);
            throw new RuntimeException("Agent not found: " + agentId);
        }

        Agent agent = agentOpt.get();
        if (!agent.isAvailable()) {
            log.warn("Agent is not available: {}", agentId);
            throw new RuntimeException("Agent is not available: " + agentId);
        }

        return agent.process(message, context);
    }

    /**
     * 获取所有可用的Agent
     *
     * @return Agent列表
     */
    public List<Agent> getAvailableAgents() {
        return agentManager.getAvailableAgents();
    }

    /**
     * 根据名称查找Agent
     *
     * @param name Agent名称
     * @return Agent列表
     */
    public List<Agent> findAgentsByName(String name) {
        return agentManager.findAgentsByName(name);
    }

    /**
     * 获取Agent信息
     *
     * @param agentId Agent ID
     * @return Agent信息
     */
    public Optional<Agent> getAgentInfo(String agentId) {
        return agentManager.getAgent(agentId);
    }

    /**
     * 获取Agent统计信息
     *
     * @return 统计信息
     */
    public AgentStatistics getStatistics() {
        List<Agent> allAgents = agentManager.getAllAgents();
        List<Agent> availableAgents = agentManager.getAvailableAgents();

        return AgentStatistics.builder()
                .totalAgents(allAgents.size())
                .availableAgents(availableAgents.size())
                .unavailableAgents(allAgents.size() - availableAgents.size())
                .build();
    }
}