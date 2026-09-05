package com.member.system.module.level.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.level.entity.MemberLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级 Mapper（注册依赖，步骤17完善）
 */
@Mapper
public interface MemberLevelMapper extends BaseMapper<MemberLevel> {
}
