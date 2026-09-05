package com.member.system.common.security;

/**
 * 密码匹配工具（新旧密码比较等）
 */
public final class PasswordMatches {

    private PasswordMatches() {
    }

    public static boolean sameRaw(String left, String right) {
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }

    public static boolean notSameRaw(String left, String right) {
        return !sameRaw(left, right);
    }
}
