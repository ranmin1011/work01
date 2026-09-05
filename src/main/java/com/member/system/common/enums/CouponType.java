package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券类型
 */
@Getter
@AllArgsConstructor
public enum CouponType {

    FULL_REDUCTION("FULL_REDUCTION", "满减券"),
    DISCOUNT("DISCOUNT", "折扣券"),
    CASH("CASH", "代金券");

    private final String code;
    private final String desc;

    public static CouponType of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (CouponType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }
}
