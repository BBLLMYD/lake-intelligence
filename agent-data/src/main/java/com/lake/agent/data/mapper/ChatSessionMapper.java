package com.lake.agent.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lake.agent.data.entity.ChatSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 聊天会话数据访问层
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Mapper
public interface ChatSessionMapper extends BaseMapper<ChatSession> {

    /**
     * 根据用户ID查询活跃会话
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    @Select("SELECT * FROM chat_session WHERE user_id = #{userId} AND status = 'ACTIVE' ORDER BY update_time DESC")
    List<ChatSession> findActiveSessionsByUserId(@Param("userId") String userId);

    /**
     * 根据Agent ID查询会话
     *
     * @param agentId Agent ID
     * @return 会话列表
     */
    @Select("SELECT * FROM chat_session WHERE agent_id = #{agentId} ORDER BY update_time DESC")
    List<ChatSession> findSessionsByAgentId(@Param("agentId") String agentId);

    /**
     * 更新会话最后活跃时间
     *
     * @param sessionId 会话ID
     */
    @Select("UPDATE chat_session SET last_active_time = NOW() WHERE id = #{sessionId}")
    void updateLastActiveTime(@Param("sessionId") String sessionId);
}