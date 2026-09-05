package com.member.system.module.auth.dto;

import lombok.Data;

/**
 * 登出请求（占位，可用于上报客户端信息）
 */
@Data
public class LogoutRequest {

    private String clientType;
    private String reason;
}
