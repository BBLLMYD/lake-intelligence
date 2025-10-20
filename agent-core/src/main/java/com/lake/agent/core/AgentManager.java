package com.lake.agent.core;

import java.util.List;
import java.util.Optional;

/**
 * Agent管理器接口
 * 负责Agent的注册、发现和生命周期管理
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
public interface AgentManager {

    /**
     * 注册Agent
     *
     * @param agent 要注册的Agent
     */
    void registerAgent(Agent agent);

    /**
     * 注销Agent
     *
     * @param agentId Agent ID
     * @return true如果注销成功，否则false
     */
    boolean unregisterAgent(String agentId);

    /**
     * 根据ID获取Agent
     *
     * @param agentId Agent ID
     * @return Agent实例，如果不存在则返回empty
     */
    Optional<Agent> getAgent(String agentId);

    /**
     * 根据名称查找Agent
     *
     * @param name Agent名称
     * @return 匹配的Agent列表
     */
    List<Agent> findAgentsByName(String name);

    /**
     * 获取所有已注册的Agent
     *
     * @return 所有Agent的列表
     */
    List<Agent> getAllAgents();

    /**
     * 获取可用的Agent列表
     *
     * @return 可用Agent的列表
     */
    List<Agent> getAvailableAgents();

    /**
     * 检查Agent是否已注册
     *
     * @param agentId Agent ID
     * @return true如果已注册，否则false
     */
    boolean isRegistered(String agentId);

    /**
     * 启动Agent管理器
     */
    void start();

    /**
     * 停止Agent管理器
     */
    void stop();

    /**
     * 获取Agent数量
     *
     * @return Agent总数
     */
    int getAgentCount();

    /**
     * 清空所有Agent
     */
    void clear();
}