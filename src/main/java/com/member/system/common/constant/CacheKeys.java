package com.member.system.common.constant;

/**
 * 缓存 Key 前缀与模板（后续接入缓存时使用）
 */
public final class CacheKeys {

    private CacheKeys() {
    }

    public static final String PREFIX = "member:";

    /** 会员详情 member:info:{memberId} */
    public static final String MEMBER_INFO = PREFIX + "info:";

    /** 会员登录失败次数 member:login:fail:{username} */
    public static final String LOGIN_FAIL = PREFIX + "login:fail:";

    /** 签到锁 member:signin:lock:{memberId}:{date} */
    public static final String SIGN_IN_LOCK = PREFIX + "signin:lock:";

    /** 等级列表缓存 */
    public static final String LEVEL_LIST = PREFIX + "level:list";

    /** 积分流水最近记录 member:points:recent:{memberId} */
    public static final String POINTS_RECENT = PREFIX + "points:recent:";

    public static String memberInfo(Long memberId) {
        return MEMBER_INFO + memberId;
    }

    public static String loginFail(String username) {
        return LOGIN_FAIL + username;
    }

    public static String signInLock(Long memberId, String date) {
        return SIGN_IN_LOCK + memberId + ":" + date;
    }

    public static String pointsRecent(Long memberId) {
        return POINTS_RECENT + memberId;
    }
}
