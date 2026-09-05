package com.member.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 会员管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.member.system.**.mapper")
public class MemberSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberSystemApplication.class, args);
    }
}
