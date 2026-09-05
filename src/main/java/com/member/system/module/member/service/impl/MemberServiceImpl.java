package com.member.system.module.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.member.system.common.auth.JwtClaims;
import com.member.system.common.auth.JwtUtil;
import com.member.system.common.auth.TokenInfo;
import com.member.system.common.constant.MemberConstants;
import com.member.system.common.enums.MemberStatus;
import com.member.system.common.enums.RegisterSource;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.security.PasswordEncoder;
import com.member.system.common.util.MemberNoGenerator;
import com.member.system.config.MemberProperties;
import com.member.system.module.auth.dto.LoginResponse;
import com.member.system.module.auth.dto.MemberLoginRequest;
import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.service.MemberLevelService;
import com.member.system.module.member.converter.MemberConverter;
import com.member.system.module.member.dto.MemberProfileUpdateRequest;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import com.member.system.module.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 会员服务实现
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MemberLevelService memberLevelService;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MemberProperties memberProperties;

    public MemberServiceImpl(MemberMapper memberMapper,
                             MemberLevelService memberLevelService,
                             MemberConverter memberConverter,
                             PasswordEncoder passwordEncoder,
                             JwtUtil jwtUtil,
                             MemberProperties memberProperties) {
        this.memberMapper = memberMapper;
        this.memberLevelService = memberLevelService;
        this.memberConverter = memberConverter;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.memberProperties = memberProperties;
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
        Member member = memberMapper.findByUsername(request.getUsername());
        BizAssert.isTrue(member != null
                        && passwordEncoder.matches(request.getPassword(), member.getPassword()),
                ErrorCodes.PASSWORD_ERROR);
        BizAssert.isTrue(MemberStatus.NORMAL.match(member.getStatus()), ErrorCodes.MEMBER_DISABLED);

        member.setLastLoginAt(LocalDateTime.now());
        memberMapper.updateById(member);

        TokenInfo tokenInfo = jwtUtil.createTokenInfo(JwtClaims.builder()
                .memberId(member.getId())
                .username(member.getUsername())
                .memberNo(member.getMemberNo())
                .build());

        return LoginResponse.builder()
                .token(tokenInfo.getAccessToken())
                .tokenType(MemberConstants.TOKEN_TYPE_BEARER)
                .expireHours(memberProperties.getJwt().getExpireHours())
                .member(getMemberVO(member.getId()))
                .build();
    }

    @Override
    public MemberVO getMemberVO(Long memberId) {
        Member member = requireMember(memberId);
        MemberLevel level = memberLevelService.getById(member.getLevelId());
        return memberConverter.toVO(member, level);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberVO updateProfile(Long memberId, MemberProfileUpdateRequest request) {
        Member member = requireMember(memberId);
        if (StringUtils.hasText(request.getNickname())) {
            member.setNickname(request.getNickname());
        }
        if (request.getEmail() != null) {
            member.setEmail(request.getEmail());
        }
        if (request.getAvatar() != null) {
            member.setAvatar(request.getAvatar());
        }
        if (request.getGender() != null) {
            member.setGender(request.getGender());
        }
        if (request.getBirthday() != null) {
            member.setBirthday(request.getBirthday());
        }
        memberMapper.updateById(member);
        return getMemberVO(memberId);
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
