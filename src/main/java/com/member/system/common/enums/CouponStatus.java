package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券状态（模板/用户券通用）
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    DRAFT(0, "草稿"),
    ENABLED(1, "启用"),
    DISABLED(2, "停用"),
    UNUSED(10, "未使用"),
    USED(11, "已使用"),
    EXPIRED(12, "已过期");

    private final int code;
    private final String desc;

    public static CouponStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (CouponStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    public boolean match(Integer code) {
        return code != null && this.code == code;
    }
}
