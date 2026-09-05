package com.member.system.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员基础视图（登录响应依赖，步骤12完善）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    private Long id;
    private String memberNo;
    private String username;
    private String nickname;
    private String mobile;
    private String email;
    private String avatar;
    private Integer gender;
    private LocalDate birthday;
    private Integer status;
    private Long levelId;
    private String levelName;
    private String levelCode;
    private Integer points;
    private Integer totalPoints;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
}
