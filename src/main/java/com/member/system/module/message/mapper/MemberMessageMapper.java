package com.member.system.module.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.message.entity.MemberMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 站内消息 Mapper
 */
@Mapper
public interface MemberMessageMapper extends BaseMapper<MemberMessage> {
}
