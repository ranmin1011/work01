package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员状态
 */
@Getter
@AllArgsConstructor
public enum MemberStatus {

    DISABLED(0, "禁用"),
    NORMAL(1, "正常");

    private final int code;
    private final String desc;

    public static MemberStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (MemberStatus status : values()) {
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
