package com.member.system.module.points.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 积分奖励请求
 */
@Data
public class PointsRewardRequest {

    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @NotNull(message = "奖励积分不能为空")
    @Min(value = 1, message = "奖励积分至少为1")
    private Integer amount;

    private String bizNo;

    private String remark;
}
