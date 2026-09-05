package com.member.system.module.auth.dto;

import com.member.system.module.member.dto.MemberVO;
import lombok.Builder;
import lombok.Data;

/**
 * 登录响应
 */
@Data
@Builder
public class LoginResponse {

    private String token;
    private String tokenType;
    private Integer expireHours;
    private MemberVO member;
}
