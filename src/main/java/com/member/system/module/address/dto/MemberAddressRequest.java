package com.member.system.module.address.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建/更新地址请求
 */
@Data
public class MemberAddressRequest {

    @NotBlank(message = "收货人不能为空")
    private String receiverName;

    @NotBlank(message = "手机号不能为空")
    private String receiverMobile;

    @NotBlank(message = "省不能为空")
    private String province;

    @NotBlank(message = "市不能为空")
    private String city;

    private String district;

    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    private Boolean isDefault;
}
