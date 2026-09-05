package com.member.system.module.coupon.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 核销优惠券请求
 */
@Data
public class CouponRedeemRequest {

    @NotNull(message = "会员优惠券ID不能为空")
    private Long memberCouponId;

    private String bizNo;
}
