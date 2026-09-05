package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用启用状态
 */
@Getter
@AllArgsConstructor
public enum EnableStatus {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private final int code;
    private final String desc;

    public static EnableStatus of(Integer code) {
        if (code == null) {
            return null;
        }
        for (EnableStatus status : values()) {
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
