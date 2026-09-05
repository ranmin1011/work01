package com.member.system.module.level.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.module.level.converter.LevelConverter;
import com.member.system.module.level.dto.MemberLevelQuery;
import com.member.system.module.level.dto.MemberLevelVO;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.mapper.MemberLevelMapper;
import com.member.system.module.level.service.MemberLevelService;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 会员等级服务实现
 */
@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    private final MemberLevelMapper memberLevelMapper;
    private final MemberMapper memberMapper;
    private final LevelConverter levelConverter;

    public MemberLevelServiceImpl(MemberLevelMapper memberLevelMapper,
                                  @Lazy MemberMapper memberMapper,
                                  LevelConverter levelConverter) {
        this.memberLevelMapper = memberLevelMapper;
        this.memberMapper = memberMapper;
        this.levelConverter = levelConverter;
    }

    @Override
    public List<MemberLevel> listEnabledLevels() {
        return memberLevelMapper.selectList(new LambdaQueryWrapper<MemberLevel>()
                .eq(MemberLevel::getStatus, 1)
                .orderByAsc(MemberLevel::getSortOrder)
                .orderByAsc(MemberLevel::getMinPoints));
    }

    @Override
    public List<MemberLevelVO> listEnabledLevelVOs() {
        return levelConverter.toVOList(listEnabledLevels());
    }

    @Override
    public MemberLevel getById(Long levelId) {
        MemberLevel level = memberLevelMapper.selectById(levelId);
        BizAssert.notNull(level, ErrorCodes.LEVEL_NOT_FOUND);
        return level;
    }

    @Override
    public MemberLevelVO getVOById(Long levelId) {
        return levelConverter.toVO(getById(levelId));
    }

    @Override
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshMemberLevel(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        int totalPoints = member.getTotalPoints() == null ? 0 : member.getTotalPoints();
        MemberLevel matched = matchLevelByTotalPoints(totalPoints);
        if (!matched.getId().equals(member.getLevelId())) {
            member.setLevelId(matched.getId());
            memberMapper.updateById(member);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberLevelVO createLevel(MemberLevel level) {
        BizAssert.notBlank(level.getLevelCode(), ErrorCodes.LEVEL_CODE_EXISTS);
        Long count = memberLevelMapper.selectCount(new LambdaQueryWrapper<MemberLevel>()
                .eq(MemberLevel::getLevelCode, level.getLevelCode()));
        BizAssert.isTrue(count == null || count == 0, ErrorCodes.LEVEL_CODE_EXISTS);
        if (level.getStatus() == null) {
            level.setStatus(1);
        }
        if (level.getSortOrder() == null) {
            level.setSortOrder(0);
        }
        if (level.getMinPoints() == null) {
            level.setMinPoints(0);
        }
        memberLevelMapper.insert(level);
        return levelConverter.toVO(level);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableLevel(Long levelId, boolean enabled) {
        MemberLevel level = getById(levelId);
        level.setStatus(enabled ? 1 : 0);
        memberLevelMapper.updateById(level);
    }

    @Override
    public List<MemberLevelVO> queryLevels(MemberLevelQuery query) {
        LambdaQueryWrapper<MemberLevel> wrapper = new LambdaQueryWrapper<MemberLevel>();
        if (query != null) {
            if (StringUtils.hasText(query.getLevelCode())) {
                wrapper.eq(MemberLevel::getLevelCode, query.getLevelCode());
            }
            if (StringUtils.hasText(query.getLevelName())) {
                wrapper.like(MemberLevel::getLevelName, query.getLevelName());
            }
            if (query.getStatus() != null) {
                wrapper.eq(MemberLevel::getStatus, query.getStatus());
            }
        }
        wrapper.orderByAsc(MemberLevel::getSortOrder).orderByAsc(MemberLevel::getMinPoints);
        return levelConverter.toVOList(memberLevelMapper.selectList(wrapper));
    }
}
