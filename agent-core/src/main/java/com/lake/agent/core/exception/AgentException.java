package com.lake.agent.core.exception;

/**
 * Agent相关异常的基类
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
public class AgentException extends RuntimeException {

    /**
     * 错误代码
     */
    private final String errorCode;

    public AgentException(String message) {
        super(message);
        this.errorCode = "AGENT_ERROR";
    }

    public AgentException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AgentException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "AGENT_ERROR";
    }

    public AgentException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}