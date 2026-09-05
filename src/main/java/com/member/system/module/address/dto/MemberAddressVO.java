package com.member.system.module.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 地址视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressVO {

    private Long id;
    private Long memberId;
    private String receiverName;
    private String receiverMobile;
    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private String fullAddress;
    private Integer isDefault;
    private LocalDateTime createdAt;
}
