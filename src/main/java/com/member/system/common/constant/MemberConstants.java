package com.member.system.common.constant;

/**
 * 会员业务常量
 */
public final class MemberConstants {

    private MemberConstants() {
    }

    /** 默认注册来源 */
    public static final String DEFAULT_REGISTER_SOURCE = "web";

    /** 默认会员等级 ID（青铜） */
    public static final long DEFAULT_LEVEL_ID = 1L;

    /** 默认头像占位 */
    public static final String DEFAULT_AVATAR = "";

    /** 会员编号前缀 */
    public static final String MEMBER_NO_PREFIX = "M";

    /** 用户名最小长度 */
    public static final int USERNAME_MIN_LENGTH = 4;

    /** 用户名最大长度 */
    public static final int USERNAME_MAX_LENGTH = 32;

    /** 密码最小长度 */
    public static final int PASSWORD_MIN_LENGTH = 6;

    /** 密码最大长度 */
    public static final int PASSWORD_MAX_LENGTH = 32;

    /** 昵称最大长度 */
    public static final int NICKNAME_MAX_LENGTH = 64;

    /** Token 类型 */
    public static final String TOKEN_TYPE_BEARER = "Bearer";

    /** 逻辑删除：未删除 */
    public static final int NOT_DELETED = 0;

    /** 逻辑删除：已删除 */
    public static final int DELETED = 1;
}
