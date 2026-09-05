package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签到奖励类型
 */
@Getter
@AllArgsConstructor
public enum SignInBonusType {

    BASE("BASE", "基础签到奖励"),
    CONTINUOUS("CONTINUOUS", "连续签到加成"),
    WEEKLY("WEEKLY", "满周额外奖励"),
    SPECIAL("SPECIAL", "活动日奖励");

    private final String code;
    private final String desc;

    public static SignInBonusType of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (SignInBonusType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }
}
