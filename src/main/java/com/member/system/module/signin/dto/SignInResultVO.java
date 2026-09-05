package com.member.system.module.signin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 签到结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResultVO {

    private Long memberId;
    private LocalDate signDate;
    private Integer continuousDays;
    private Integer pointsEarned;
    private Integer basePoints;
    private Integer bonusPoints;
    private Integer balanceAfter;
    private Boolean signedToday;
}
