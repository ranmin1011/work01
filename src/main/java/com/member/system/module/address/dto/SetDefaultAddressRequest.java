package com.member.system.module.address.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设置默认地址请求
 */
@Data
public class SetDefaultAddressRequest {

    @NotNull(message = "地址ID不能为空")
    private Long addressId;
}
