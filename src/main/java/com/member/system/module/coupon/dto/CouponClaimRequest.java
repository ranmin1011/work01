package com.member.system.module.coupon.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 领取优惠券请求
 */
@Data
public class CouponClaimRequest {

    @NotNull(message = "优惠券ID不能为空")
    private Long couponId;
}
