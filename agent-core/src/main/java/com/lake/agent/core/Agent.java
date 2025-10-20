package com.lake.agent.core;

import com.lake.agent.core.message.Message;
import com.lake.agent.core.context.AgentContext;

/**
 * 核心Agent接口
 * 定义AI Agent的基本行为规范
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
public interface Agent {

    /**
     * 获取Agent的唯一标识
     *
     * @return Agent ID
     */
    String getId();

    /**
     * 获取Agent名称
     *
     * @return Agent名称
     */
    String getName();

    /**
     * 获取Agent描述信息
     *
     * @return Agent描述
     */
    String getDescription();

    /**
     * 处理消息
     *
     * @param message 输入消息
     * @param context Agent执行上下文
     * @return 处理结果消息
     */
    Message process(Message message, AgentContext context);

    /**
     * 检查Agent是否可用
     *
     * @return true如果可用，否则false
     */
    boolean isAvailable();

    /**
     * 初始化Agent
     */
    void initialize();

    /**
     * 销毁Agent，释放资源
     */
    void destroy();
}