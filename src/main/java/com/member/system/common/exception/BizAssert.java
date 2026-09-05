package com.member.system.common.exception;

import com.member.system.common.result.ResultCode;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 业务断言工具：条件不满足时抛出 BusinessException
 */
public final class BizAssert {

    private BizAssert() {
    }

    public static void isTrue(boolean expression, ResultCode resultCode) {
        if (!expression) {
            throw new BusinessException(resultCode);
        }
    }

    public static void isTrue(boolean expression, ErrorCodes errorCodes) {
        if (!expression) {
            throw new BusinessException(errorCodes);
        }
    }

    public static void isTrue(boolean expression, ErrorCodes errorCodes, String message) {
        if (!expression) {
            throw new BusinessException(errorCodes, message);
        }
    }

    public static void notNull(Object object, ErrorCodes errorCodes) {
        if (object == null) {
            throw new BusinessException(errorCodes);
        }
    }

    public static void notBlank(String text, ErrorCodes errorCodes) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(errorCodes);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCodes errorCodes) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(errorCodes);
        }
    }

    public static void isFalse(boolean expression, ErrorCodes errorCodes) {
        if (expression) {
            throw new BusinessException(errorCodes);
        }
    }
}
