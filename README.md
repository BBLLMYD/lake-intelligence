# Lake Intelligence AI Agent Framework

## 项目概述

Lake Intelligence 是一个基于 Spring Boot 开发的 Java AI Agent 框架，提供了完整的 AI Agent 开发、部署和管理解决方案。

## 架构设计

项目采用模块化设计，包含以下5个核心模块：

### 📦 模块结构

```
lake-intelligence/
├── agent-core/                    # Agent 核心模块
│   ├── Agent 接口定义
│   ├── 消息处理机制
│   ├── 上下文管理
│   └── 异常处理
├── agent-framework/               # 框架核心配置
│   ├── 自动配置
│   ├── 配置属性
│   └── Agent 管理器
├── agent-tools/                   # 工具集模块
│   ├── HTTP 请求工具
│   ├── 文件处理工具
│   └── 工具接口定义
├── agent-service/                 # 业务服务模块
│   ├── Agent 服务
│   ├── 简单AI Agent实现
│   └── 统计信息服务
├── agent-data/                    # 数据持久层模块
│   ├── 实体定义
│   ├── 数据访问层
│   └── 数据库配置
└── agent-api/                     # 统一API层
    ├── REST API接口
    ├── 主启动类
    ├── 配置管理
    └── Swagger文档
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Spring Boot 3.2+

### 运行示例应用

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd lake-intelligence
   ```

2. **编译项目**
   ```bash
   mvn clean compile
   ```

3. **运行API应用**
   ```bash
   cd agent-api
   mvn spring-boot:run
   ```

4. **访问应用**
   - 应用地址: http://localhost:8080
   - H2 数据库控制台: http://localhost:8080/h2-console

## 🔧 使用指南

### 1. 添加依赖

在你的 Spring Boot 项目中添加以下依赖：

```xml
<dependency>
    <groupId>com.lake</groupId>
    <artifactId>agent-framework</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 2. 配置文件

在 `application.yml` 中添加 Agent 配置：

```yaml
lake:
  agent:
    enabled: true
    manager:
      max-agents: 50
      timeout: 30000
      monitoring: true
    ai:
      provider: openai
      models:
        default: gpt-3.5-turbo
    tools:
      enabled: true
      scan-packages:
        - com.your.package.tools
```

### 3. 创建自定义 Agent

```java
@Component
public class CustomAgent extends AbstractAgent {

    public CustomAgent() {
        super("Custom Agent", "My custom AI agent");
    }

    @Override
    protected Message doProcess(Message message, AgentContext context) {
        // 实现你的业务逻辑
        String response = processMessage(message.getContent());
        return createResponseMessage(response, message);
    }

    private String processMessage(String content) {
        // 处理消息的具体逻辑
        return "处理结果: " + content;
    }
}
```

### 4. 使用 Agent 服务

```java
@RestController
public class MyController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/chat")
    public Message chat(@RequestBody ChatRequest request) {
        Message message = Message.builder()
            .content(request.getMessage())
            .type(MessageType.TEXT)
            .sender(request.getUserId())
            .build();

        AgentContext context = AgentContext.builder()
            .userId(request.getUserId())
            .sessionId(request.getSessionId())
            .build();

        return agentService.processMessage(request.getAgentId(), message, context);
    }
}
```

## 🛠️ 核心功能

### Agent 管理
- ✅ Agent 注册和发现
- ✅ 生命周期管理
- ✅ 状态监控
- ✅ 负载均衡

### 消息处理
- ✅ 多种消息类型支持
- ✅ 消息路由和分发
- ✅ 异步处理
- ✅ 错误处理和重试

### 工具集成
- ✅ HTTP 请求工具
- ✅ 文件处理工具
- ✅ 自定义工具接口
- ✅ 工具链组合

### 数据持久化
- ✅ 会话管理
- ✅ 消息历史
- ✅ MyBatis Plus 集成
- ✅ 多数据库支持

## 📊 API 接口

### Agent 管理 API

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/v1/agents` | 获取所有可用Agent |
| GET | `/api/v1/agents/{id}` | 获取指定Agent信息 |
| GET | `/api/v1/agents/search?name={name}` | 搜索Agent |
| POST | `/api/v1/agents/{id}/chat` | 与Agent对话 |
| GET | `/api/v1/agents/statistics` | 获取统计信息 |

### 示例请求

**与 Agent 对话:**
```bash
curl -X POST http://localhost:8080/api/v1/agents/{agentId}/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Hello, how are you?",
    "userId": "user123",
    "sessionId": "session456"
  }'
```

## 🔍 监控和调试

### 性能监控
- Agent 执行时间统计
- 消息处理成功率

## 🧪 测试

运行所有测试：
```bash
mvn test
```

运行集成测试：
```bash
mvn integration-test
```

## 📈 扩展开发

### 自定义工具

实现 `Tool` 接口创建自定义工具：

```java
@Component
public class MyCustomTool implements Tool {

    @Override
    public String getName() {
        return "my_custom_tool";
    }

    @Override
    public ToolResult execute(ToolInput input, AgentContext context) {
        // 实现工具逻辑
        return ToolResult.success("执行结果");
    }
}
```

### 自定义 Agent 管理器

实现 `AgentManager` 接口可以自定义 Agent 管理逻辑：

```java
@Component
public class MyAgentManager implements AgentManager {
    // 实现自定义管理逻辑
}
```


  