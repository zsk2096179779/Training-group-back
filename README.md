# 因子管理系统

一个基于Vue 3 + Element Plus + Java Spring Boot的因子管理系统。

## 功能特性

- 🔐 **用户认证系统**：支持用户注册、登录和JWT令牌管理
- 🌳 **因子树管理**：支持多级层次结构的因子树展示和管理
- 📊 **因子管理**：支持普通因子和派生因子的创建和管理
- 🔗 **数据源配置**：支持外部数据源的配置和数据导入
- 📈 **数据分析**：提供因子分析功能

## 前端技术栈

- Vue 3 (Composition API)
- Element Plus
- Vue Router
- Axios

## 认证系统

### 登录功能
- 访问 `/login` 或根路径 `/` 进入登录页面
- 输入用户名和密码进行登录
- 登录成功后自动跳转到数据源配置页面

### 注册功能
- 访问 `/register` 进入注册页面
- 输入用户名、邮箱和密码进行注册
- 注册成功后跳转到登录页面

### 令牌管理
- 登录成功后，JWT令牌自动保存到localStorage
- 所有API请求自动携带Authorization头
- 令牌过期时自动跳转到登录页面

### 路由保护
- 需要认证的页面会自动检查登录状态
- 未登录用户访问受保护页面时自动重定向到登录页

## 开发说明

### 启动开发服务器
```bash
npm install
npm run dev
```

### 构建生产版本
```bash
npm run build
```

## API接口

### 认证相关
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/register` - 用户注册
- `GET /api/auth/user` - 获取用户信息
- `POST /api/auth/logout` - 用户登出

### 数据源配置
- `GET /api/datasource` - 获取数据源列表
- `POST /api/datasource` - 创建数据源
- `PUT /api/datasource/:id` - 更新数据源
- `DELETE /api/datasource/:id` - 删除数据源
- `POST /api/datasource/:id/import` - 导入数据

### 因子管理
- `GET /api/factors` - 获取因子列表
- `POST /api/factors` - 创建因子
- `PUT /api/factors/:id` - 更新因子
- `DELETE /api/factors/:id` - 删除因子

### 因子树管理
- `GET /api/factor-tree` - 获取因子树
- `POST /api/factor-tree` - 创建因子树节点
- `PUT /api/factor-tree/:id` - 更新因子树节点
- `DELETE /api/factor-tree/:id` - 删除因子树节点

## 项目结构

```
src/
├── api/           # API接口
│   ├── auth.js    # 认证相关接口
│   ├── datasource.js
│   ├── factor.js
│   └── request.js # Axios配置
├── components/    # 组件
│   ├── Navbar.vue
│   └── ...
├── router/        # 路由配置
│   └── portfolios.js
├── utils/         # 工具函数
│   └── auth.js    # 认证工具
├── views/         # 页面
│   ├── Login.vue
│   ├── Register.vue
│   └── ...
├── App.vue
└── main.js
```
