# 会员管理系统

基于 Spring Boot 2.7 + MyBatis-Plus + JWT 的会员管理系统，覆盖注册登录、等级、积分、签到、地址、优惠券、站内消息与管理端查询。

## 技术栈

- Java 8 / Spring Boot 2.7.18
- MyBatis-Plus 3.5.5 / MySQL 8
- JWT（jjwt）/ Springdoc OpenAPI / Lombok / AOP

## 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8（默认库名 `member_db`）

## 快速启动

1. 创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS member_db DEFAULT CHARACTER SET utf8mb4;
```

2. 修改 `src/main/resources/application.yml` 中的数据库账号密码。

3. 启动：

```bash
mvn spring-boot:run
```

4. 访问：

| 用途 | 路径 |
|------|------|
| 服务端口 | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| 健康检查 | GET `/health` |
| Ping | GET `/ping` |

## 主要 API

### 认证（无需 Token）

- `POST /auth/register` 注册
- `POST /auth/login` 登录

### 会员

- `GET /members/me` 当前会员资料
- `PUT /members/me` 更新资料

### 等级（公开）

- `GET /levels` 启用等级列表
- `GET /levels/{id}` 等级详情
- `GET/POST/PUT /admin/levels/**` 管理端等级

### 积分

- `GET /points/balance` 余额
- `GET /points/records` 流水
- `POST /points/consume` 消费
- `POST /admin/points/adjust` 人工调整
- `POST /admin/points/reward` 发放奖励

### 签到

- `POST /sign-in` 今日签到
- `GET /sign-in/today` 签到状态
- `GET /sign-in/records` 签到记录

### 地址

- `GET/POST /addresses` 列表 / 新增
- `PUT/DELETE /addresses/{id}` 更新 / 删除
- `GET/PUT /addresses/default` 默认地址

### 优惠券

- `GET /coupons/available` 可领取列表
- `POST /coupons/claim` 领取
- `GET /coupons/mine` 我的优惠券
- `POST /coupons/redeem` 核销

### 消息

- `GET /messages` 消息分页
- `GET /messages/unread-count` 未读数
- `PUT /messages/{id}/read` 标记已读

### 管理端会员

- `GET /admin/members` 分页查询
- `PUT /admin/members/{id}/enable` 启用
- `PUT /admin/members/{id}/disable` 禁用

> 除标注公开的接口外，请求头需携带：`Authorization: Bearer <token>`。

## 模块结构

```text
com.member.system
├── common/          # 响应、异常、枚举、JWT、鉴权
├── config/          # Web / MyBatis / OpenAPI / 业务配置
└── module/
    ├── auth/        # 认证
    ├── member/      # 会员与管理端
    ├── level/       # 等级
    ├── points/      # 积分
    ├── signin/      # 签到
    ├── address/     # 地址
    ├── coupon/      # 优惠券
    ├── message/     # 站内消息
    ├── operlog/     # 操作日志
    ├── event/       # 领域事件
    └── health/      # 健康检查
```

## 配置说明

`application.yml` 中 `member` 节点可配置：

- `jwt.secret` / `jwt.expire-hours`
- `points.register-bonus` / `points.sign-in-bonus`
- `sign-in.continuous-cycle-days` / `sign-in.continuous-cycle-bonus`
