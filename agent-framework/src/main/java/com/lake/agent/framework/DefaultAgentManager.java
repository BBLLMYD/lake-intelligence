package com.lake.agent.framework;

import com.lake.agent.core.Agent;
import com.lake.agent.core.AgentManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 默认Agent管理器实现
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
public class DefaultAgentManager implements AgentManager {

    private final ConcurrentMap<String, Agent> agents = new ConcurrentHashMap<>();
    private final AgentProperties properties;
    private volatile boolean started = false;

    public DefaultAgentManager(AgentProperties properties) {
        this.properties = properties;
    }

    @Override
    public void registerAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }

        if (agents.size() >= properties.getManager().getMaxAgents()) {
            throw new IllegalStateException("Maximum number of agents exceeded: " +
                    properties.getManager().getMaxAgents());
        }

        String agentId = agent.getId();
        if (!StringUtils.hasText(agentId)) {
            throw new IllegalArgumentException("Agent ID cannot be empty");
        }

        agents.put(agentId, agent);

        // 初始化Agent
        try {
            agent.initialize();
            log.info("Agent registered and initialized: {} ({})", agent.getName(), agentId);
        } catch (Exception e) {
            agents.remove(agentId);
            log.error("Failed to initialize agent: {} ({})", agent.getName(), agentId, e);
            throw new RuntimeException("Failed to initialize agent: " + agentId, e);
        }
    }

    @Override
    public boolean unregisterAgent(String agentId) {
        if (!StringUtils.hasText(agentId)) {
            return false;
        }

        Agent agent = agents.remove(agentId);
        if (agent != null) {
            try {
                agent.destroy();
                log.info("Agent unregistered and destroyed: {} ({})", agent.getName(), agentId);
                return true;
            } catch (Exception e) {
                log.error("Error destroying agent: {} ({})", agent.getName(), agentId, e);
                return false;
            }
        }
        return false;
    }

    @Override
    public Optional<Agent> getAgent(String agentId) {
        return Optional.ofNullable(agents.get(agentId));
    }

    @Override
    public List<Agent> findAgentsByName(String name) {
        if (!StringUtils.hasText(name)) {
            return getAllAgents();
        }

        return agents.values().stream()
                .filter(agent -> name.equals(agent.getName()) ||
                        (agent.getName() != null && agent.getName().contains(name)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Agent> getAllAgents() {
        return agents.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Agent> getAvailableAgents() {
        return agents.values().stream()
                .filter(Agent::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRegistered(String agentId) {
        return agents.containsKey(agentId);
    }

    @Override
    public void start() {
        if (started) {
            log.warn("AgentManager is already started");
            return;
        }

        log.info("Starting AgentManager with {} agents", agents.size());
        started = true;
        log.info("AgentManager started successfully");
    }

    @Override
    public void stop() {
        if (!started) {
            log.warn("AgentManager is not started");
            return;
        }

        log.info("Stopping AgentManager...");

        // 销毁所有Agent
        agents.values().forEach(agent -> {
            try {
                agent.destroy();
                log.debug("Agent destroyed: {} ({})", agent.getName(), agent.getId());
            } catch (Exception e) {
                log.error("Error destroying agent: {} ({})", agent.getName(), agent.getId(), e);
            }
        });

        agents.clear();
        started = false;
        log.info("AgentManager stopped");
    }

    @Override
    public int getAgentCount() {
        return agents.size();
    }

    @Override
    public void clear() {
        log.info("Clearing all agents...");
        stop();
        start();
    }
}