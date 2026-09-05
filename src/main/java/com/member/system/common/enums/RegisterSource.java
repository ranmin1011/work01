package com.member.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注册来源
 */
@Getter
@AllArgsConstructor
public enum RegisterSource {

    WEB("web", "网页"),
    APP("app", "App"),
    WECHAT("wechat", "微信"),
    ADMIN("admin", "后台录入"),
    OTHER("other", "其他");

    private final String code;
    private final String desc;

    public static RegisterSource of(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (RegisterSource source : values()) {
            if (source.code.equalsIgnoreCase(code)) {
                return source;
            }
        }
        return OTHER;
    }
}
