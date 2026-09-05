package com.member.system.common.auth;

/**
 * 当前登录会员上下文
 */
public final class MemberContext {

    private static final ThreadLocal<Long> MEMBER_ID = new ThreadLocal<Long>();
    private static final ThreadLocal<JwtClaims> CLAIMS = new ThreadLocal<JwtClaims>();

    private MemberContext() {
    }

    public static void set(JwtClaims claims) {
        CLAIMS.set(claims);
        MEMBER_ID.set(claims.getMemberId());
    }

    public static void setMemberId(Long memberId) {
        MEMBER_ID.set(memberId);
    }

    public static Long getMemberId() {
        return MEMBER_ID.get();
    }

    public static JwtClaims getClaims() {
        return CLAIMS.get();
    }

    public static void clear() {
        MEMBER_ID.remove();
        CLAIMS.remove();
    }
}
