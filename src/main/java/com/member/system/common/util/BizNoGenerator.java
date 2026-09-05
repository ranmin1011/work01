package com.member.system.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 业务单号生成
 */
public final class BizNoGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private BizNoGenerator() {
    }

    public static String memberNo() {
        int random = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "M" + LocalDateTime.now().format(FORMATTER) + random;
    }

    public static String pointsBizNo(String prefix) {
        int random = ThreadLocalRandom.current().nextInt(10000, 99999);
        return prefix + LocalDateTime.now().format(FORMATTER) + random;
    }
}
