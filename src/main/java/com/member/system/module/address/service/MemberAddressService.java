package com.member.system.module.address.service;

import com.member.system.module.address.dto.MemberAddressRequest;
import com.member.system.module.address.dto.MemberAddressVO;

import java.util.List;

/**
 * 会员地址服务
 */
public interface MemberAddressService {

    MemberAddressVO create(Long memberId, MemberAddressRequest request);

    MemberAddressVO update(Long memberId, Long addressId, MemberAddressRequest request);

    void delete(Long memberId, Long addressId);

    List<MemberAddressVO> listByMember(Long memberId);

    MemberAddressVO getDefault(Long memberId);

    void setDefault(Long memberId, Long addressId);
}
