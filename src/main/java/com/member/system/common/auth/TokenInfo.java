package com.member.system.common.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Token 信息视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String accessToken;
    private String tokenType;
    private Integer expireHours;
    private LocalDateTime expireAt;
}
