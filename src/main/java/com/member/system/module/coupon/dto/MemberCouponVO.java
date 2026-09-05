package com.member.system.module.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 会员优惠券视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCouponVO {

    private Long id;
    private Long memberId;
    private Long couponId;
    private String couponCode;
    private String couponName;
    private Integer status;
    private String statusDesc;
    private LocalDateTime claimedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expireAt;
}
