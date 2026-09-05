package com.member.system.module.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.member.system.common.constant.MemberConstants;
import com.member.system.common.enums.MemberStatus;
import com.member.system.common.enums.RegisterSource;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.BusinessException;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.security.PasswordEncoder;
import com.member.system.common.util.MemberNoGenerator;
import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.service.MemberLevelService;
import com.member.system.module.member.converter.MemberConverter;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import com.member.system.module.member.service.MemberService;
import com.member.system.module.auth.dto.LoginResponse;
import com.member.system.module.auth.dto.MemberLoginRequest;
import com.member.system.module.member.dto.MemberProfileUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 会员服务实现 —— 步骤14：注册
 */
@Service
public class MemberServiceImpl implements MemberService {

    protected final MemberMapper memberMapper;
    protected final MemberLevelService memberLevelService;
    protected final MemberConverter memberConverter;
    protected final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberMapper memberMapper,
                             MemberLevelService memberLevelService,
                             MemberConverter memberConverter,
                             PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.memberLevelService = memberLevelService;
        this.memberConverter = memberConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberVO register(MemberRegisterRequest request) {
        Long usernameCount = memberMapper.selectCount(new LambdaQueryWrapper<Member>()
                .eq(Member::getUsername, request.getUsername()));
        BizAssert.isTrue(usernameCount == null || usernameCount == 0, ErrorCodes.USERNAME_EXISTS);

        if (StringUtils.hasText(request.getMobile())) {
            Long mobileCount = memberMapper.selectCount(new LambdaQueryWrapper<Member>()
                    .eq(Member::getMobile, request.getMobile()));
            BizAssert.isTrue(mobileCount == null || mobileCount == 0, ErrorCodes.MOBILE_EXISTS);
        }

        MemberLevel defaultLevel = memberLevelService.matchLevelByTotalPoints(0);
        Member member = new Member();
        member.setMemberNo(MemberNoGenerator.next());
        member.setUsername(request.getUsername());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setNickname(StringUtils.hasText(request.getNickname()) ? request.getNickname() : request.getUsername());
        member.setMobile(StringUtils.hasText(request.getMobile()) ? request.getMobile() : null);
        member.setEmail(request.getEmail());
        member.setGender(0);
        member.setStatus(MemberStatus.NORMAL.getCode());
        member.setLevelId(defaultLevel.getId());
        member.setPoints(0);
        member.setTotalPoints(0);
        RegisterSource source = RegisterSource.of(request.getRegisterSource());
        member.setRegisterSource(source == null ? MemberConstants.DEFAULT_REGISTER_SOURCE : source.getCode());
        memberMapper.insert(member);
        return memberConverter.toVO(member, defaultLevel);
    }

    @Override
    public LoginResponse login(MemberLoginRequest request) {
        throw new BusinessException(ErrorCodes.MEMBER_NOT_FOUND, "登录能力在步骤15实现");
    }

    @Override
    public MemberVO getMemberVO(Long memberId) {
        Member member = requireMember(memberId);
        MemberLevel level = memberLevelService.getById(member.getLevelId());
        return memberConverter.toVO(member, level);
    }

    @Override
    public MemberVO updateProfile(Long memberId, MemberProfileUpdateRequest request) {
        throw new BusinessException(ErrorCodes.MEMBER_PROFILE_INVALID, "资料更新在步骤15实现");
    }

    @Override
    public Member requireMember(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        return member;
    }

    @Override
    public void refreshLevel(Long memberId) {
        Member member = requireMember(memberId);
        int totalPoints = member.getTotalPoints() == null ? 0 : member.getTotalPoints();
        MemberLevel matched = memberLevelService.matchLevelByTotalPoints(totalPoints);
        if (!matched.getId().equals(member.getLevelId())) {
            member.setLevelId(matched.getId());
            memberMapper.updateById(member);
        }
    }
}
