package com.member.system.module.signin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.signin.entity.MemberSignIn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到 Mapper
 */
@Mapper
public interface MemberSignInMapper extends BaseMapper<MemberSignIn> {
}
