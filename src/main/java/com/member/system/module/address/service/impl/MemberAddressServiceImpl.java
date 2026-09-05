package com.member.system.module.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.module.address.converter.MemberAddressConverter;
import com.member.system.module.address.dto.MemberAddressRequest;
import com.member.system.module.address.dto.MemberAddressVO;
import com.member.system.module.address.entity.MemberAddress;
import com.member.system.module.address.mapper.MemberAddressMapper;
import com.member.system.module.address.service.MemberAddressService;
import com.member.system.module.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员地址服务实现
 */
@Service
public class MemberAddressServiceImpl implements MemberAddressService {

    private final MemberAddressMapper memberAddressMapper;
    private final MemberAddressConverter memberAddressConverter;
    private final MemberService memberService;

    public MemberAddressServiceImpl(MemberAddressMapper memberAddressMapper,
                                    MemberAddressConverter memberAddressConverter,
                                    MemberService memberService) {
        this.memberAddressMapper = memberAddressMapper;
        this.memberAddressConverter = memberAddressConverter;
        this.memberService = memberService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberAddressVO create(Long memberId, MemberAddressRequest request) {
        memberService.requireMember(memberId);
        MemberAddress address = new MemberAddress();
        address.setMemberId(memberId);
        memberAddressConverter.applyRequest(address, request);
        if (address.getIsDefault() == null) {
            Long count = memberAddressMapper.selectCount(new LambdaQueryWrapper<MemberAddress>()
                    .eq(MemberAddress::getMemberId, memberId));
            address.setIsDefault((count == null || count == 0) ? 1 : 0);
        }
        if (Integer.valueOf(1).equals(address.getIsDefault())) {
            clearDefault(memberId);
        }
        memberAddressMapper.insert(address);
        return memberAddressConverter.toVO(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberAddressVO update(Long memberId, Long addressId, MemberAddressRequest request) {
        MemberAddress address = requireOwned(memberId, addressId);
        memberAddressConverter.applyRequest(address, request);
        if (Integer.valueOf(1).equals(address.getIsDefault())) {
            clearDefault(memberId);
            address.setIsDefault(1);
        }
        memberAddressMapper.updateById(address);
        return memberAddressConverter.toVO(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long memberId, Long addressId) {
        MemberAddress address = requireOwned(memberId, addressId);
        memberAddressMapper.deleteById(address.getId());
    }

    @Override
    public List<MemberAddressVO> listByMember(Long memberId) {
        List<MemberAddress> list = memberAddressMapper.selectList(new LambdaQueryWrapper<MemberAddress>()
                .eq(MemberAddress::getMemberId, memberId)
                .orderByDesc(MemberAddress::getIsDefault)
                .orderByDesc(MemberAddress::getId));
        return memberAddressConverter.toVOList(list);
    }

    @Override
    public MemberAddressVO getDefault(Long memberId) {
        MemberAddress address = memberAddressMapper.selectOne(new LambdaQueryWrapper<MemberAddress>()
                .eq(MemberAddress::getMemberId, memberId)
                .eq(MemberAddress::getIsDefault, 1)
                .last("LIMIT 1"));
        return memberAddressConverter.toVO(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Long memberId, Long addressId) {
        requireOwned(memberId, addressId);
        clearDefault(memberId);
        MemberAddress address = new MemberAddress();
        address.setId(addressId);
        address.setIsDefault(1);
        memberAddressMapper.updateById(address);
    }

    private MemberAddress requireOwned(Long memberId, Long addressId) {
        MemberAddress address = memberAddressMapper.selectById(addressId);
        BizAssert.notNull(address, ErrorCodes.MEMBER_NOT_FOUND);
        BizAssert.isTrue(memberId.equals(address.getMemberId()), ErrorCodes.MEMBER_NOT_FOUND);
        return address;
    }

    private void clearDefault(Long memberId) {
        memberAddressMapper.update(null, new LambdaUpdateWrapper<MemberAddress>()
                .eq(MemberAddress::getMemberId, memberId)
                .eq(MemberAddress::getIsDefault, 1)
                .set(MemberAddress::getIsDefault, 0));
    }
}
