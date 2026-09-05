package com.member.system.module.coupon.service;

import com.member.system.module.coupon.dto.CouponClaimRequest;
import com.member.system.module.coupon.dto.CouponRedeemRequest;
import com.member.system.module.coupon.dto.CouponVO;
import com.member.system.module.coupon.dto.MemberCouponVO;

import java.util.List;

/**
 * 优惠券服务
 */
public interface CouponService {

    List<CouponVO> listAvailable();

    MemberCouponVO claim(Long memberId, CouponClaimRequest request);

    List<MemberCouponVO> myCoupons(Long memberId, Integer status);

    MemberCouponVO redeem(Long memberId, CouponRedeemRequest request);
}
