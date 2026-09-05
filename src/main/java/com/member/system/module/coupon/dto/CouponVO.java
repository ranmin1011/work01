package com.member.system.module.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券模板视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponVO {

    private Long id;
    private String couponCode;
    private String couponName;
    private String couponType;
    private String couponTypeDesc;
    private BigDecimal thresholdAmount;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private Integer totalCount;
    private Integer claimedCount;
    private Integer remainCount;
    private Integer perMemberLimit;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private Integer status;
}
