package com.member.system.module.coupon.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 优惠券查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponQuery extends PageQuery {

    private String couponType;
    private Integer status;
}
