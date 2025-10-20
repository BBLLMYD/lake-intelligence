package com.lake.agent.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Agent统计信息
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentStatistics {

    /**
     * 总Agent数量
     */
    private int totalAgents;

    /**
     * 可用Agent数量
     */
    private int availableAgents;

    /**
     * 不可用Agent数量
     */
    private int unavailableAgents;

    /**
     * 活跃度百分比
     */
    public double getAvailabilityPercentage() {
        return totalAgents > 0 ? (double) availableAgents / totalAgents * 100 : 0;
    }
}