package com.member.system.module.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.member.system.common.enums.CouponStatus;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.module.coupon.converter.CouponConverter;
import com.member.system.module.coupon.dto.CouponClaimRequest;
import com.member.system.module.coupon.dto.CouponRedeemRequest;
import com.member.system.module.coupon.dto.CouponVO;
import com.member.system.module.coupon.dto.MemberCouponVO;
import com.member.system.module.coupon.entity.Coupon;
import com.member.system.module.coupon.entity.MemberCoupon;
import com.member.system.module.coupon.mapper.CouponMapper;
import com.member.system.module.coupon.mapper.MemberCouponMapper;
import com.member.system.module.coupon.service.CouponService;
import com.member.system.module.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券领取与核销服务
 */
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponMapper couponMapper;
    private final MemberCouponMapper memberCouponMapper;
    private final CouponConverter couponConverter;
    private final MemberService memberService;

    public CouponServiceImpl(CouponMapper couponMapper,
                             MemberCouponMapper memberCouponMapper,
                             CouponConverter couponConverter,
                             MemberService memberService) {
        this.couponMapper = couponMapper;
        this.memberCouponMapper = memberCouponMapper;
        this.couponConverter = couponConverter;
        this.memberService = memberService;
    }

    @Override
    public List<CouponVO> listAvailable() {
        LocalDateTime now = LocalDateTime.now();
        List<Coupon> coupons = couponMapper.selectList(new LambdaQueryWrapper<Coupon>()
                .eq(Coupon::getStatus, CouponStatus.ENABLED.getCode())
                .and(w -> w.isNull(Coupon::getValidFrom).or().le(Coupon::getValidFrom, now))
                .and(w -> w.isNull(Coupon::getValidTo).or().ge(Coupon::getValidTo, now))
                .orderByDesc(Coupon::getId));
        List<CouponVO> result = new ArrayList<CouponVO>();
        for (Coupon coupon : coupons) {
            result.add(couponConverter.toVO(coupon));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberCouponVO claim(Long memberId, CouponClaimRequest request) {
        memberService.requireMember(memberId);
        Coupon coupon = couponMapper.selectById(request.getCouponId());
        BizAssert.notNull(coupon, ErrorCodes.COUPON_NOT_FOUND);
        BizAssert.isTrue(CouponStatus.ENABLED.match(coupon.getStatus()), ErrorCodes.COUPON_NOT_USABLE);

        LocalDateTime now = LocalDateTime.now();
        if (coupon.getValidTo() != null) {
            BizAssert.isTrue(!now.isAfter(coupon.getValidTo()), ErrorCodes.COUPON_EXPIRED);
        }
        int claimed = coupon.getClaimedCount() == null ? 0 : coupon.getClaimedCount();
        int total = coupon.getTotalCount() == null ? 0 : coupon.getTotalCount();
        BizAssert.isTrue(claimed < total, ErrorCodes.COUPON_EXHAUSTED);

        int limit = coupon.getPerMemberLimit() == null ? 1 : coupon.getPerMemberLimit();
        Long mine = memberCouponMapper.selectCount(new LambdaQueryWrapper<MemberCoupon>()
                .eq(MemberCoupon::getMemberId, memberId)
                .eq(MemberCoupon::getCouponId, coupon.getId()));
        BizAssert.isTrue(mine == null || mine < limit, ErrorCodes.COUPON_ALREADY_CLAIMED);

        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setMemberId(memberId);
        memberCoupon.setCouponId(coupon.getId());
        memberCoupon.setCouponCode(coupon.getCouponCode());
        memberCoupon.setStatus(CouponStatus.UNUSED.getCode());
        memberCoupon.setClaimedAt(now);
        memberCoupon.setExpireAt(coupon.getValidTo());
        memberCouponMapper.insert(memberCoupon);

        coupon.setClaimedCount(claimed + 1);
        couponMapper.updateById(coupon);
        return couponConverter.toMemberVO(memberCoupon, coupon);
    }

    @Override
    public List<MemberCouponVO> myCoupons(Long memberId, Integer status) {
        LambdaQueryWrapper<MemberCoupon> wrapper = new LambdaQueryWrapper<MemberCoupon>()
                .eq(MemberCoupon::getMemberId, memberId)
                .orderByDesc(MemberCoupon::getId);
        if (status != null) {
            wrapper.eq(MemberCoupon::getStatus, status);
        }
        List<MemberCoupon> list = memberCouponMapper.selectList(wrapper);
        List<MemberCouponVO> result = new ArrayList<MemberCouponVO>();
        for (MemberCoupon item : list) {
            Coupon coupon = couponMapper.selectById(item.getCouponId());
            result.add(couponConverter.toMemberVO(item, coupon));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberCouponVO redeem(Long memberId, CouponRedeemRequest request) {
        MemberCoupon memberCoupon = memberCouponMapper.selectById(request.getMemberCouponId());
        BizAssert.notNull(memberCoupon, ErrorCodes.COUPON_NOT_FOUND);
        BizAssert.isTrue(memberId.equals(memberCoupon.getMemberId()), ErrorCodes.COUPON_NOT_USABLE);
        BizAssert.isTrue(CouponStatus.UNUSED.match(memberCoupon.getStatus()), ErrorCodes.COUPON_NOT_USABLE);
        if (memberCoupon.getExpireAt() != null) {
            BizAssert.isTrue(!LocalDateTime.now().isAfter(memberCoupon.getExpireAt()), ErrorCodes.COUPON_EXPIRED);
        }
        memberCoupon.setStatus(CouponStatus.USED.getCode());
        memberCoupon.setUsedAt(LocalDateTime.now());
        memberCouponMapper.updateById(memberCoupon);
        Coupon coupon = couponMapper.selectById(memberCoupon.getCouponId());
        return couponConverter.toMemberVO(memberCoupon, coupon);
    }
}
