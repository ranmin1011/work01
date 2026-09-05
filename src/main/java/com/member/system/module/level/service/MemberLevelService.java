package com.member.system.module.level.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.mapper.MemberLevelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 等级服务最小实现（供注册使用，步骤18增强）
 */
@Service
public class MemberLevelService {

    private final MemberLevelMapper memberLevelMapper;

    public MemberLevelService(MemberLevelMapper memberLevelMapper) {
        this.memberLevelMapper = memberLevelMapper;
    }

    public List<MemberLevel> listEnabledLevels() {
        return memberLevelMapper.selectList(new LambdaQueryWrapper<MemberLevel>()
                .eq(MemberLevel::getStatus, 1)
                .orderByAsc(MemberLevel::getSortOrder));
    }

    public MemberLevel getById(Long levelId) {
        MemberLevel level = memberLevelMapper.selectById(levelId);
        BizAssert.notNull(level, ErrorCodes.LEVEL_NOT_FOUND);
        return level;
    }

    public MemberLevel matchLevelByTotalPoints(int totalPoints) {
        List<MemberLevel> levels = listEnabledLevels();
        BizAssert.notEmpty(levels, ErrorCodes.LEVEL_NOT_FOUND);
        MemberLevel matched = levels.get(0);
        for (MemberLevel level : levels) {
            if (totalPoints >= level.getMinPoints()) {
                matched = level;
            }
        }
        return matched;
    }
}
