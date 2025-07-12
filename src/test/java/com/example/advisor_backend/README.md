# 交易管理系统测试类说明

## 概述

本目录包含了交易管理子系统的完整测试类，包括单元测试、集成测试和测试工具类。

## 测试类结构

### 1. 单元测试类

#### TradingServiceTest.java
- **位置**: `src/test/java/com/example/advisor_backend/service/TradingServiceTest.java`
- **功能**: 测试TradingService的所有业务方法
- **测试覆盖**:
  - 订单列表查询
  - 批量提交订单
  - 取消订单
  - 错误订单处理
  - 交割单查询
  - 调仓功能
  - 基金查询
  - 账户管理

#### TradingControllerTest.java
- **位置**: `src/test/java/com/example/advisor_backend/controller/TradingControllerTest.java`
- **功能**: 测试TradingController的所有API端点
- **测试覆盖**:
  - REST API端点测试
  - 请求参数验证
  - 响应状态码验证
  - JSON响应格式验证

### 2. 集成测试类

#### TradingIntegrationTest.java
- **位置**: `src/test/java/com/example/advisor_backend/integration/TradingIntegrationTest.java`
- **功能**: 端到端集成测试，测试完整的业务流程
- **测试覆盖**:
  - 完整的交易工作流
  - 数据库持久化
  - 服务层方法
  - 错误处理
  - 数据验证

### 3. 测试工具类

#### TestDataBuilder.java
- **位置**: `src/test/java/com/example/advisor_backend/util/TestDataBuilder.java`
- **功能**: 提供测试数据构建方法
- **包含**:
  - 各种DTO的创建方法
  - 实体对象的创建方法
  - 测试数据列表的创建方法

## 测试配置

### 测试配置文件
- **位置**: `src/test/resources/application-test.yml`
- **配置内容**:
  - H2内存数据库配置
  - JPA配置
  - MyBatis-Plus配置
  - 日志配置

## 运行测试

### 运行所有测试
```bash
mvn test
```

### 运行特定测试类
```bash
mvn test -Dtest=TradingServiceTest
mvn test -Dtest=TradingControllerTest
mvn test -Dtest=TradingIntegrationTest
```

### 运行特定测试方法
```bash
mvn test -Dtest=TradingServiceTest#testListOrders_Success
```

## 测试覆盖范围

### 业务功能测试
1. **订单管理**
   - 订单列表查询（分页、过滤）
   - 批量提交订单
   - 取消订单
   - 订单执行/拒绝

2. **错误处理**
   - 错误订单查询
   - 错误订单修复
   - 错误订单替换

3. **调仓功能**
   - 组合调仓
   - 账户调仓
   - 调仓详情查询

4. **基金研究**
   - 基金列表查询
   - 基金搜索过滤

5. **交割单管理**
   - 交割单查询
   - 交割单状态管理

6. **账户管理**
   - 账户列表查询
   - 账户详情查询
   - 账户调仓提交

### API端点测试
- GET `/api/trading/orders` - 订单列表
- POST `/api/trading/orders` - 提交订单
- DELETE `/api/trading/orders/{orderId}` - 取消订单
- GET `/api/trading/orders/errors` - 错误订单列表
- POST `/api/trading/errors/fix` - 修复错误订单
- GET `/api/trading/fills` - 交割单列表
- POST `/api/trading/rebalance` - 执行调仓
- POST `/api/trading/orders/execute` - 执行订单
- POST `/api/trading/orders/reject` - 拒绝订单
- GET `/api/trading/delivery-notes` - 交割单列表
- POST `/api/trading/error/replace` - 替换错误订单
- GET `/api/trading/fund-research/list` - 基金列表
- GET `/api/trading/account-rebalance/accounts` - 账户列表
- GET `/api/trading/account-rebalance/detail` - 账户详情
- POST `/api/trading/account-rebalance` - 提交账户调仓

## 测试数据

### 测试实体
- **Fund**: 基金信息
- **Account**: 账户信息
- **Order**: 订单信息
- **ErrorOrder**: 错误订单信息
- **FillOrder**: 交割单信息

### 测试DTO
- **OrderRequestDTO**: 订单请求
- **OrderResponseDTO**: 订单响应
- **ErrorFixDTO**: 错误修复请求
- **RebalancePayload**: 调仓载荷
- **AccountRebalancePayload**: 账户调仓载荷
- **FundDTO**: 基金信息DTO
- **AccountDTO**: 账户信息DTO

## 测试最佳实践

### 1. 测试命名规范
- 测试方法名使用 `test[方法名]_[场景]` 格式
- 例如: `testListOrders_Success`, `testCancelOrder_OrderNotFound`

### 2. 测试结构
- 使用 Given-When-Then 结构
- Given: 准备测试数据
- When: 执行被测试的方法
- Then: 验证结果

### 3. 测试数据管理
- 使用 TestDataBuilder 创建测试数据
- 每个测试方法使用独立的测试数据
- 测试完成后清理测试数据

### 4. 断言验证
- 验证返回值
- 验证方法调用
- 验证状态变化
- 验证异常处理

## 注意事项

1. **数据库隔离**: 集成测试使用事务回滚，确保测试数据不会影响其他测试
2. **Mock使用**: 单元测试使用Mock对象隔离依赖
3. **测试数据**: 使用独立的测试数据，避免测试间相互影响
4. **性能考虑**: 测试应该快速执行，避免长时间等待

## 扩展测试

### 添加新的测试用例
1. 在相应的测试类中添加新的测试方法
2. 使用 TestDataBuilder 创建测试数据
3. 遵循测试命名规范和结构
4. 添加必要的断言验证

### 添加新的测试类
1. 创建新的测试类文件
2. 添加必要的注解和依赖注入
3. 实现测试方法
4. 更新本文档

## 故障排除

### 常见问题
1. **测试失败**: 检查测试数据是否正确设置
2. **数据库连接问题**: 确认H2数据库配置正确
3. **Mock对象问题**: 检查Mock对象的设置和验证
4. **依赖注入问题**: 确认Spring上下文正确加载

### 调试技巧
1. 使用 `@Test` 注解的 `timeout` 属性设置超时时间
2. 使用 `@Disabled` 注解临时禁用测试
3. 使用 `System.out.println()` 或日志输出调试信息
4. 使用IDE的调试功能逐步执行测试 