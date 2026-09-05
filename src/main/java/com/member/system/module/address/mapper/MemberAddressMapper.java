package com.member.system.module.address.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.address.entity.MemberAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员地址 Mapper
 */
@Mapper
public interface MemberAddressMapper extends BaseMapper<MemberAddress> {
}
