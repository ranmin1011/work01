package com.member.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 会员业务配置（JWT、积分奖励等）
 */
@Data
@Component
@ConfigurationProperties(prefix = "member")
public class MemberProperties {

    private Jwt jwt = new Jwt();
    private Points points = new Points();
    private SignIn signIn = new SignIn();

    @Data
    public static class Jwt {
        /** HMAC 密钥，生产环境请替换 */
        private String secret = "member-system-jwt-secret-key-change-me";
        /** Token 有效期（小时） */
        private int expireHours = 24;
        /** 请求头前缀 */
        private String tokenPrefix = "Bearer ";
    }

    @Data
    public static class Points {
        /** 注册赠送积分 */
        private int registerBonus = 100;
        /** 每日签到基础积分 */
        private int signInBonus = 10;
    }

    @Data
    public static class SignIn {
        /** 连续签到满 N 天额外奖励 */
        private int continuousCycleDays = 7;
        /** 满周期额外积分 */
        private int continuousCycleBonus = 5;
    }
}
