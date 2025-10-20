package com.lake.agent.tools.file;

import cn.hutool.core.io.FileUtil;
import com.lake.agent.core.context.AgentContext;
import com.lake.agent.tools.Tool;
import com.lake.agent.tools.ToolInput;
import com.lake.agent.tools.ToolResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 文件处理工具
 * 支持文件读写操作
 *
 * @author Lake Intelligence Team
 * @since 1.0.0
 */
@Slf4j
@Component
public class FileProcessorTool implements Tool {

    @Override
    public String getName() {
        return "file_processor";
    }

    @Override
    public String getDescription() {
        return "Read and write files from the local filesystem";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public ToolResult execute(ToolInput input, AgentContext context) {
        long startTime = System.currentTimeMillis();

        try {
            String operation = input.getParameter("operation");
            String filePath = input.getParameter("filePath");

            log.debug("Executing file {} operation for: {}", operation, filePath);

            Object result = switch (operation.toLowerCase()) {
                case "read" -> readFile(filePath);
                case "write" -> writeFile(filePath, input.getParameter("content", ""));
                case "exists" -> FileUtil.exist(filePath);
                case "delete" -> deleteFile(filePath);
                case "create" -> createFile(filePath);
                case "size" -> getFileSize(filePath);
                default -> throw new IllegalArgumentException("Unsupported operation: " + operation);
            };

            long executionTime = System.currentTimeMillis() - startTime;

            return ToolResult.builder()
                    .success(true)
                    .data(result)
                    .executionTime(executionTime)
                    .build();

        } catch (Exception e) {
            log.error("File operation failed", e);
            long executionTime = System.currentTimeMillis() - startTime;

            return ToolResult.builder()
                    .success(false)
                    .errorMessage("File operation failed: " + e.getMessage())
                    .executionTime(executionTime)
                    .build();
        }
    }

    @Override
    public boolean validate(ToolInput input) {
        String operation = input.getParameter("operation");
        if (!StringUtils.hasText(operation)) {
            log.warn("Operation parameter is required");
            return false;
        }

        String filePath = input.getParameter("filePath");
        if (!StringUtils.hasText(filePath)) {
            log.warn("FilePath parameter is required");
            return false;
        }

        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    private String readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        return FileUtil.readString(file, StandardCharsets.UTF_8);
    }

    private boolean writeFile(String filePath, String content) {
        FileUtil.writeString(content, new File(filePath), StandardCharsets.UTF_8);
        return true;
    }

    private boolean deleteFile(String filePath) {
        return FileUtil.del(filePath);
    }

    private boolean createFile(String filePath) {
        File file = new File(filePath);
        return FileUtil.touch(file) != null;
    }

    private long getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        return file.length();
    }
}