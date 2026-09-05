package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员等级编码
 */
@Getter
@AllArgsConstructor
public enum LevelCode {

    BRONZE("BRONZE", "青铜会员", 1),
    SILVER("SILVER", "白银会员", 2),
    GOLD("GOLD", "黄金会员", 3),
    PLATINUM("PLATINUM", "铂金会员", 4),
    DIAMOND("DIAMOND", "钻石会员", 5);

    private final String code;
    private final String desc;
    private final int sortOrder;

    public static LevelCode of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (LevelCode levelCode : values()) {
            if (levelCode.code.equalsIgnoreCase(code)) {
                return levelCode;
            }
        }
        return null;
    }
}
