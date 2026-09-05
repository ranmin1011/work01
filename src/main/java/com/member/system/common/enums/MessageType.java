package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 站内消息类型
 */
@Getter
@AllArgsConstructor
public enum MessageType {

    SYSTEM("SYSTEM", "系统通知"),
    POINTS("POINTS", "积分变动"),
    LEVEL("LEVEL", "等级变更"),
    COUPON("COUPON", "优惠券"),
    MARKETING("MARKETING", "营销活动");

    private final String code;
    private final String desc;

    public static MessageType of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (MessageType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }
}
