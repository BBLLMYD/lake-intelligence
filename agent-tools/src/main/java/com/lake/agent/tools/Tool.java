package com.lake.agent.tools;

import com.lake.agent.core.context.AgentContext;

/**
 * 工具接口
 * 定义Agent可以使用的工具规范
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
public interface Tool {

    /**
     * 获取工具名称
     *
     * @return 工具名称
     */
    String getName();

    /**
     * 获取工具描述
     *
     * @return 工具描述
     */
    String getDescription();

    /**
     * 获取工具版本
     *
     * @return 工具版本
     */
    String getVersion();

    /**
     * 执行工具
     *
     * @param input   输入参数
     * @param context Agent上下文
     * @return 执行结果
     */
    ToolResult execute(ToolInput input, AgentContext context);

    /**
     * 验证输入参数
     *
     * @param input 输入参数
     * @return true如果验证通过，否则false
     */
    boolean validate(ToolInput input);

    /**
     * 检查工具是否可用
     *
     * @return true如果可用，否则false
     */
    boolean isAvailable();
}