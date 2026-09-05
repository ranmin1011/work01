package com.member.system.module.coupon.converter;

import com.member.system.common.enums.CouponStatus;
import com.member.system.common.enums.CouponType;
import com.member.system.module.coupon.dto.CouponVO;
import com.member.system.module.coupon.dto.MemberCouponVO;
import com.member.system.module.coupon.entity.Coupon;
import com.member.system.module.coupon.entity.MemberCoupon;
import org.springframework.stereotype.Component;

/**
 * 优惠券转换
 */
@Component
public class CouponConverter {

    public CouponVO toVO(Coupon coupon) {
        if (coupon == null) {
            return null;
        }
        CouponType type = CouponType.of(coupon.getCouponType());
        int claimed = coupon.getClaimedCount() == null ? 0 : coupon.getClaimedCount();
        int total = coupon.getTotalCount() == null ? 0 : coupon.getTotalCount();
        return CouponVO.builder()
                .id(coupon.getId())
                .couponCode(coupon.getCouponCode())
                .couponName(coupon.getCouponName())
                .couponType(coupon.getCouponType())
                .couponTypeDesc(type == null ? coupon.getCouponType() : type.getDesc())
                .thresholdAmount(coupon.getThresholdAmount())
                .discountAmount(coupon.getDiscountAmount())
                .discountRate(coupon.getDiscountRate())
                .totalCount(coupon.getTotalCount())
                .claimedCount(coupon.getClaimedCount())
                .remainCount(Math.max(total - claimed, 0))
                .perMemberLimit(coupon.getPerMemberLimit())
                .validFrom(coupon.getValidFrom())
                .validTo(coupon.getValidTo())
                .status(coupon.getStatus())
                .build();
    }

    public MemberCouponVO toMemberVO(MemberCoupon memberCoupon, Coupon coupon) {
        if (memberCoupon == null) {
            return null;
        }
        CouponStatus status = CouponStatus.of(memberCoupon.getStatus());
        MemberCouponVO.MemberCouponVOBuilder builder = MemberCouponVO.builder()
                .id(memberCoupon.getId())
                .memberId(memberCoupon.getMemberId())
                .couponId(memberCoupon.getCouponId())
                .couponCode(memberCoupon.getCouponCode())
                .status(memberCoupon.getStatus())
                .statusDesc(status == null ? String.valueOf(memberCoupon.getStatus()) : status.getDesc())
                .claimedAt(memberCoupon.getClaimedAt())
                .usedAt(memberCoupon.getUsedAt())
                .expireAt(memberCoupon.getExpireAt());
        if (coupon != null) {
            builder.couponName(coupon.getCouponName());
        }
        return builder.build();
    }
}
