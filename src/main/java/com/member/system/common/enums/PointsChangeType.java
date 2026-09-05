package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分变动类型
 */
@Getter
@AllArgsConstructor
public enum PointsChangeType {

    REGISTER("REGISTER", "注册赠送"),
    SIGN_IN("SIGN_IN", "每日签到"),
    CONSUME("CONSUME", "消费抵扣"),
    REWARD("REWARD", "活动奖励"),
    ADJUST("ADJUST", "人工调整"),
    LEVEL_UP("LEVEL_UP", "升级奖励"),
    EXPIRE("EXPIRE", "积分过期");

    private final String code;
    private final String desc;

    public static PointsChangeType of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (PointsChangeType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }
}
