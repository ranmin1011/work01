package com.member.system.common.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT 声明载荷
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaims {

    private Long memberId;
    private String username;
    private String memberNo;
}
