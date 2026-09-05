package com.member.system.module.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Token 刷新请求（占位）
 */
@Data
public class TokenRefreshRequest {

    @NotBlank(message = "refreshToken不能为空")
    private String refreshToken;
}
