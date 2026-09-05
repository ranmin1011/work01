package com.member.system.module.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.member.system.common.enums.MemberStatus;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.result.PageResult;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.service.MemberLevelService;
import com.member.system.module.member.dto.AdminMemberQuery;
import com.member.system.module.member.dto.AdminMemberVO;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import com.member.system.module.member.service.AdminMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理端会员服务实现
 */
@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final MemberMapper memberMapper;
    private final MemberLevelService memberLevelService;

    public AdminMemberServiceImpl(MemberMapper memberMapper, MemberLevelService memberLevelService) {
        this.memberMapper = memberMapper;
        this.memberLevelService = memberLevelService;
    }

    @Override
    public PageResult<AdminMemberVO> pageMembers(AdminMemberQuery query) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<Member>();
        if (query != null) {
            if (StringUtils.hasText(query.getUsername())) {
                wrapper.like(Member::getUsername, query.getUsername());
            }
            if (StringUtils.hasText(query.getMobile())) {
                wrapper.eq(Member::getMobile, query.getMobile());
            }
            if (StringUtils.hasText(query.getMemberNo())) {
                wrapper.eq(Member::getMemberNo, query.getMemberNo());
            }
            if (StringUtils.hasText(query.getNickname())) {
                wrapper.like(Member::getNickname, query.getNickname());
            }
            if (query.getStatus() != null) {
                wrapper.eq(Member::getStatus, query.getStatus());
            }
            if (query.getLevelId() != null) {
                wrapper.eq(Member::getLevelId, query.getLevelId());
            }
        }
        wrapper.orderByDesc(Member::getId);
        long pageNo = query == null ? 1L : query.current();
        long pageSize = query == null ? 10L : query.size();
        Page<Member> page = memberMapper.selectPage(new Page<Member>(pageNo, pageSize), wrapper);
        List<AdminMemberVO> records = new ArrayList<AdminMemberVO>();
        for (Member member : page.getRecords()) {
            records.add(toAdminVO(member));
        }
        return PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        member.setStatus(MemberStatus.NORMAL.getCode());
        memberMapper.updateById(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disable(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        member.setStatus(MemberStatus.DISABLED.getCode());
        memberMapper.updateById(member);
    }

    private AdminMemberVO toAdminVO(Member member) {
        AdminMemberVO.AdminMemberVOBuilder builder = AdminMemberVO.builder()
                .id(member.getId())
                .memberNo(member.getMemberNo())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .mobile(member.getMobile())
                .email(member.getEmail())
                .status(member.getStatus())
                .levelId(member.getLevelId())
                .points(member.getPoints())
                .totalPoints(member.getTotalPoints())
                .registerSource(member.getRegisterSource())
                .lastLoginAt(member.getLastLoginAt())
                .createdAt(member.getCreatedAt());
        if (member.getLevelId() != null) {
            try {
                MemberLevel level = memberLevelService.getById(member.getLevelId());
                builder.levelName(level.getLevelName()).levelCode(level.getLevelCode());
            } catch (Exception ignored) {
                // level may be missing
            }
        }
        return builder.build();
    }
}
