package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum GenderType {

    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private final int code;
    private final String desc;

    public static GenderType of(Integer code) {
        if (code == null) {
            return null;
        }
        for (GenderType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
