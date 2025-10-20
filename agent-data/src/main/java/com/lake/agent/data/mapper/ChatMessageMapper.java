package com.lake.agent.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lake.agent.data.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 聊天消息数据访问层
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

    /**
     * 根据会话ID查询消息列表
     *
     * @param sessionId 会话ID
     * @param limit     限制数量
     * @return 消息列表
     */
    @Select("SELECT * FROM chat_message WHERE session_id = #{sessionId} ORDER BY create_time ASC LIMIT #{limit}")
    List<ChatMessage> findMessagesBySessionId(@Param("sessionId") String sessionId, @Param("limit") int limit);

    /**
     * 根据会话ID查询最新消息
     *
     * @param sessionId 会话ID
     * @param count     消息数量
     * @return 消息列表
     */
    @Select("SELECT * FROM chat_message WHERE session_id = #{sessionId} ORDER BY create_time DESC LIMIT #{count}")
    List<ChatMessage> findLatestMessagesBySessionId(@Param("sessionId") String sessionId, @Param("count") int count);

    /**
     * 统计会话消息数量
     *
     * @param sessionId 会话ID
     * @return 消息数量
     */
    @Select("SELECT COUNT(*) FROM chat_message WHERE session_id = #{sessionId}")
    long countMessagesBySessionId(@Param("sessionId") String sessionId);
}