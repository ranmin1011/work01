package com.member.system.common.constant;

/**
 * HTTP 请求头常量
 */
public final class HeaderConstants {

    private HeaderConstants() {
    }

    /** 认证头 */
    public static final String AUTHORIZATION = "Authorization";

    /** Bearer 前缀 */
    public static final String BEARER_PREFIX = "Bearer ";

    /** 请求追踪 ID */
    public static final String TRACE_ID = "X-Trace-Id";

    /** 客户端类型 */
    public static final String CLIENT_TYPE = "X-Client-Type";

    /** 客户端版本 */
    public static final String CLIENT_VERSION = "X-Client-Version";

    /** 真实 IP（经网关转发时） */
    public static final String REAL_IP = "X-Real-IP";

    /** 转发 IP 链 */
    public static final String FORWARDED_FOR = "X-Forwarded-For";
}
