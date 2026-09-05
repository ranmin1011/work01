package com.member.system.module.points.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 积分人工调整请求
 */
@Data
public class PointsAdjustRequest {

    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @NotNull(message = "调整积分不能为空")
    private Integer amount;

    private String remark;
}
