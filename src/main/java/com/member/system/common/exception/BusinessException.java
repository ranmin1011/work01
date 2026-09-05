package com.member.system.common.exception;

import com.member.system.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常：可预期的业务失败，由全局处理器转换为 ApiResult
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public BusinessException(ErrorCodes errorCodes) {
        super(errorCodes.getMessage());
        this.code = errorCodes.getCode();
    }

    public BusinessException(ErrorCodes errorCodes, String message) {
        super(message);
        this.code = errorCodes.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
