package com.member.system.module.points.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 积分消费请求
 */
@Data
public class PointsConsumeRequest {

    @NotNull(message = "消费积分不能为空")
    @Min(value = 1, message = "消费积分至少为1")
    private Integer amount;

    private String bizNo;

    private String remark;
}
