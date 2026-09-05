package com.member.system.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理端会员视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberVO {

    private Long id;
    private String memberNo;
    private String username;
    private String nickname;
    private String mobile;
    private String email;
    private Integer status;
    private Long levelId;
    private String levelName;
    private String levelCode;
    private Integer points;
    private Integer totalPoints;
    private String registerSource;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
}
