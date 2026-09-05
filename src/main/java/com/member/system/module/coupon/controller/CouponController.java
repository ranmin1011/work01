package com.member.system.module.coupon.controller;

import com.member.system.common.annotation.OperLog;
import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.module.coupon.dto.CouponClaimRequest;
import com.member.system.module.coupon.dto.CouponRedeemRequest;
import com.member.system.module.coupon.dto.CouponVO;
import com.member.system.module.coupon.dto.MemberCouponVO;
import com.member.system.module.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 优惠券接口
 */
@Tag(name = "优惠券")
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @Operation(summary = "可领取优惠券列表")
    @GetMapping("/available")
    public ApiResult<List<CouponVO>> available() {
        return ApiResult.ok(couponService.listAvailable());
    }

    @Operation(summary = "领取优惠券")
    @OperLog(module = "优惠券", value = "领取优惠券")
    @PostMapping("/claim")
    public ApiResult<MemberCouponVO> claim(@Validated @RequestBody CouponClaimRequest request) {
        return ApiResult.ok(couponService.claim(MemberContext.getMemberId(), request), "领取成功");
    }

    @Operation(summary = "我的优惠券")
    @GetMapping("/mine")
    public ApiResult<List<MemberCouponVO>> mine(@RequestParam(value = "status", required = false) Integer status) {
        return ApiResult.ok(couponService.myCoupons(MemberContext.getMemberId(), status));
    }

    @Operation(summary = "核销优惠券")
    @OperLog(module = "优惠券", value = "核销优惠券")
    @PostMapping("/redeem")
    public ApiResult<MemberCouponVO> redeem(@Validated @RequestBody CouponRedeemRequest request) {
        return ApiResult.ok(couponService.redeem(MemberContext.getMemberId(), request), "核销成功");
    }
}
