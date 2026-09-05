package com.member.system.module.points.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 积分余额视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsBalanceVO {

    private Long memberId;
    private Integer points;
}
