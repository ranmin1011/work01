package com.member.system.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务域错误码（会员及相关模块预留）
 * <p>
 * 号段约定：
 * 1000-1099 会员
 * 1100-1199 等级
 * 1200-1299 积分
 * 1300-1399 签到
 * 1400-1499 优惠券
 * 1500-1599 消息
 */
@Getter
@AllArgsConstructor
public enum ErrorCodes {

    // ---- 会员 1000-1099 ----
    MEMBER_NOT_FOUND(1001, "会员不存在"),
    MEMBER_DISABLED(1002, "会员已禁用"),
    USERNAME_EXISTS(1003, "用户名已存在"),
    MOBILE_EXISTS(1004, "手机号已注册"),
    EMAIL_EXISTS(1005, "邮箱已注册"),
    PASSWORD_ERROR(1006, "用户名或密码错误"),
    PASSWORD_SAME_AS_OLD(1007, "新密码不能与旧密码相同"),
    MEMBER_PROFILE_INVALID(1008, "会员资料不合法"),

    // ---- 等级 1100-1199 ----
    LEVEL_NOT_FOUND(1101, "会员等级不存在"),
    LEVEL_DISABLED(1102, "会员等级已禁用"),
    LEVEL_CODE_EXISTS(1103, "等级编码已存在"),

    // ---- 积分 1200-1299 ----
    POINTS_INSUFFICIENT(1201, "积分不足"),
    POINTS_CHANGE_INVALID(1202, "积分变动不合法"),
    POINTS_RECORD_NOT_FOUND(1203, "积分流水不存在"),

    // ---- 签到 1300-1399 ----
    ALREADY_SIGNED_IN(1301, "今日已签到"),
    SIGN_IN_NOT_ALLOWED(1302, "当前不可签到"),

    // ---- 优惠券 1400-1499 ----
    COUPON_NOT_FOUND(1401, "优惠券不存在"),
    COUPON_EXHAUSTED(1402, "优惠券已领完"),
    COUPON_EXPIRED(1403, "优惠券已过期"),
    COUPON_ALREADY_CLAIMED(1404, "已领取过该优惠券"),
    COUPON_NOT_USABLE(1405, "优惠券不可用"),

    // ---- 消息 1500-1599 ----
    MESSAGE_NOT_FOUND(1501, "消息不存在"),
    MESSAGE_ALREADY_READ(1502, "消息已读");

    private final int code;
    private final String message;
}
