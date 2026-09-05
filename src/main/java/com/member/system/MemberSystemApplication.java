package com.member.system;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 会员管理系统启动类
 * <p>
 * 脚手架阶段暂排除数据源与 MyBatis-Plus 自动配置，接入数据库后再移除 exclude。
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class
})
public class MemberSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberSystemApplication.class, args);
    }
}
