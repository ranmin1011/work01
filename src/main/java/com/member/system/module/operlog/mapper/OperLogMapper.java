package com.member.system.module.operlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.member.system.module.operlog.entity.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {
}
