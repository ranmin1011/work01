package com.member.system.module.signin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 签到记录视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRecordVO {

    private Long id;
    private Long memberId;
    private LocalDate signDate;
    private Integer continuousDays;
    private Integer pointsEarned;
    private LocalDateTime createdAt;
}
