# 会员管理系统（脚手架）

基于 Spring Boot 2.7 + MyBatis-Plus 的会员系统项目骨架，当前仅完成脚手架搭建，业务代码待后续迭代。

## 技术栈

- Java 8
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.5
- MySQL 8
- Lombok

## 目录结构

```text
member-system
├── pom.xml
├── README.md
└── src/main
    ├── java/com/member/system
    │   ├── MemberSystemApplication.java   # 启动类
    │   ├── common/                        # 通用层（待实现）
    │   ├── config/                        # 配置层（待实现）
    │   └── module/
    │       ├── member/                    # 会员模块（待实现）
    │       ├── level/                     # 等级模块（待实现）
    │       └── points/                    # 积分模块（待实现）
    └── resources
        ├── application.yml
        ├── db/                            # SQL 脚本（待实现）
        └── mapper/                        # Mapper XML（待实现）
```

## 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8（本地默认库名 `member_db`）

## 快速启动

1. 创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS member_db DEFAULT CHARACTER SET utf8mb4;
```

2. 修改 `src/main/resources/application.yml` 中的数据库账号密码。

3. 启动项目：

```bash
mvn spring-boot:run
```

或在 IDE 中运行 `MemberSystemApplication`。

4. 默认端口：`http://localhost:8080`

> 说明：脚手架阶段尚未接入业务接口与表结构，启动后需先完成数据库与模块实现再验证接口。

## 后续计划

1. 通用层：统一响应、全局异常、业务枚举
2. 会员模块：注册 / 登录 / 资料
3. 等级与积分模块
4. 数据库脚本与 API 文档
