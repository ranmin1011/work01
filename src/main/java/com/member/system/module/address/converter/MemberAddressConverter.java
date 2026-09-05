package com.member.system.module.address.converter;

import com.member.system.module.address.dto.MemberAddressRequest;
import com.member.system.module.address.dto.MemberAddressVO;
import com.member.system.module.address.entity.MemberAddress;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 地址转换
 */
@Component
public class MemberAddressConverter {

    public MemberAddressVO toVO(MemberAddress address) {
        if (address == null) {
            return null;
        }
        StringBuilder full = new StringBuilder();
        if (StringUtils.hasText(address.getProvince())) {
            full.append(address.getProvince());
        }
        if (StringUtils.hasText(address.getCity())) {
            full.append(address.getCity());
        }
        if (StringUtils.hasText(address.getDistrict())) {
            full.append(address.getDistrict());
        }
        if (StringUtils.hasText(address.getDetailAddress())) {
            full.append(address.getDetailAddress());
        }
        return MemberAddressVO.builder()
                .id(address.getId())
                .memberId(address.getMemberId())
                .receiverName(address.getReceiverName())
                .receiverMobile(address.getReceiverMobile())
                .province(address.getProvince())
                .city(address.getCity())
                .district(address.getDistrict())
                .detailAddress(address.getDetailAddress())
                .fullAddress(full.toString())
                .isDefault(address.getIsDefault())
                .createdAt(address.getCreatedAt())
                .build();
    }

    public List<MemberAddressVO> toVOList(List<MemberAddress> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<MemberAddressVO> result = new ArrayList<MemberAddressVO>(list.size());
        for (MemberAddress item : list) {
            result.add(toVO(item));
        }
        return result;
    }

    public void applyRequest(MemberAddress address, MemberAddressRequest request) {
        address.setReceiverName(request.getReceiverName());
        address.setReceiverMobile(request.getReceiverMobile());
        address.setProvince(request.getProvince());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setDetailAddress(request.getDetailAddress());
        if (request.getIsDefault() != null) {
            address.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()) ? 1 : 0);
        }
    }
}
