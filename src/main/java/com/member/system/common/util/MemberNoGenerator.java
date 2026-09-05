package com.member.system.common.util;

/**
 * 会员编号生成器
 */
public final class MemberNoGenerator {

    private MemberNoGenerator() {
    }

    public static String next() {
        return BizNoGenerator.memberNo();
    }
}
