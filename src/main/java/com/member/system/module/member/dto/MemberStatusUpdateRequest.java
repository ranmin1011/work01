package com.member.system.module.member.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 会员状态变更请求
 */
@Data
public class MemberStatusUpdateRequest {

    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态取值无效")
    @Max(value = 1, message = "状态取值无效")
    private Integer status;
}
