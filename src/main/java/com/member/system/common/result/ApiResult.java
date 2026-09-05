package com.member.system.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一 API 响应包装
 *
 * @param <T> 业务数据类型
 */
@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 业务码，0 表示成功 */
    private int code;

    /** 提示信息 */
    private String message;

    /** 业务数据 */
    private T data;

    /** 响应时间戳（毫秒） */
    private long timestamp;

    public ApiResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ApiResult<T> ok() {
        return ok(null);
    }

    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        ApiResult<T> result = ok(data);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> fail(ResultCode resultCode) {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    public static <T> ApiResult<T> fail(ResultCode resultCode, String message) {
        ApiResult<T> result = fail(resultCode);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> fail(int code, String message) {
        ApiResult<T> result = new ApiResult<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
}
