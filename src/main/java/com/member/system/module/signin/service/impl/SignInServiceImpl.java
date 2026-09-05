package com.member.system.module.signin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.member.system.common.enums.PointsChangeType;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.result.PageResult;
import com.member.system.common.util.BizNoGenerator;
import com.member.system.config.MemberProperties;
import com.member.system.module.member.service.MemberService;
import com.member.system.module.points.dto.PointsGrantCommand;
import com.member.system.module.points.facade.PointsFacade;
import com.member.system.module.signin.converter.SignInConverter;
import com.member.system.module.signin.dto.SignInRecordQuery;
import com.member.system.module.signin.dto.SignInRecordVO;
import com.member.system.module.signin.dto.SignInResultVO;
import com.member.system.module.signin.entity.MemberSignIn;
import com.member.system.module.signin.mapper.MemberSignInMapper;
import com.member.system.module.signin.service.SignInService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * 签到领域服务实现
 */
@Service
public class SignInServiceImpl implements SignInService {

    private final MemberSignInMapper memberSignInMapper;
    private final PointsFacade pointsFacade;
    private final MemberService memberService;
    private final MemberProperties memberProperties;
    private final SignInConverter signInConverter;

    public SignInServiceImpl(MemberSignInMapper memberSignInMapper,
                             PointsFacade pointsFacade,
                             MemberService memberService,
                             MemberProperties memberProperties,
                             SignInConverter signInConverter) {
        this.memberSignInMapper = memberSignInMapper;
        this.pointsFacade = pointsFacade;
        this.memberService = memberService;
        this.memberProperties = memberProperties;
        this.signInConverter = signInConverter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignInResultVO signIn(Long memberId) {
        memberService.requireMember(memberId);
        LocalDate today = LocalDate.now();
        MemberSignIn todayRecord = memberSignInMapper.selectOne(new LambdaQueryWrapper<MemberSignIn>()
                .eq(MemberSignIn::getMemberId, memberId)
                .eq(MemberSignIn::getSignDate, today));
        BizAssert.isTrue(todayRecord == null, ErrorCodes.ALREADY_SIGNED_IN);

        int continuousDays = 1;
        MemberSignIn yesterday = memberSignInMapper.selectOne(new LambdaQueryWrapper<MemberSignIn>()
                .eq(MemberSignIn::getMemberId, memberId)
                .eq(MemberSignIn::getSignDate, today.minusDays(1)));
        if (yesterday != null && yesterday.getContinuousDays() != null) {
            continuousDays = yesterday.getContinuousDays() + 1;
        }

        int basePoints = memberProperties.getPoints().getSignInBonus();
        int bonusPoints = 0;
        int cycleDays = memberProperties.getSignIn().getContinuousCycleDays();
        if (cycleDays > 0 && continuousDays % cycleDays == 0) {
            bonusPoints = memberProperties.getSignIn().getContinuousCycleBonus();
        }
        int earned = basePoints + bonusPoints;

        MemberSignIn record = new MemberSignIn();
        record.setMemberId(memberId);
        record.setSignDate(today);
        record.setContinuousDays(continuousDays);
        record.setPointsEarned(earned);
        memberSignInMapper.insert(record);

        if (earned > 0) {
            pointsFacade.grant(PointsGrantCommand.builder()
                    .memberId(memberId)
                    .amount(earned)
                    .changeType(PointsChangeType.SIGN_IN)
                    .bizNo(BizNoGenerator.pointsBizNo("SI"))
                    .remark("每日签到奖励，连续" + continuousDays + "天")
                    .build());
        }
        memberService.refreshLevel(memberId);

        return SignInResultVO.builder()
                .memberId(memberId)
                .signDate(today)
                .continuousDays(continuousDays)
                .pointsEarned(earned)
                .basePoints(basePoints)
                .bonusPoints(bonusPoints)
                .balanceAfter(pointsFacade.getBalance(memberId))
                .signedToday(true)
                .build();
    }

    @Override
    public SignInResultVO todayStatus(Long memberId) {
        memberService.requireMember(memberId);
        LocalDate today = LocalDate.now();
        MemberSignIn todayRecord = memberSignInMapper.selectOne(new LambdaQueryWrapper<MemberSignIn>()
                .eq(MemberSignIn::getMemberId, memberId)
                .eq(MemberSignIn::getSignDate, today));
        if (todayRecord == null) {
            return SignInResultVO.builder()
                    .memberId(memberId)
                    .signDate(today)
                    .signedToday(false)
                    .continuousDays(0)
                    .pointsEarned(0)
                    .balanceAfter(pointsFacade.getBalance(memberId))
                    .build();
        }
        return SignInResultVO.builder()
                .memberId(memberId)
                .signDate(todayRecord.getSignDate())
                .continuousDays(todayRecord.getContinuousDays())
                .pointsEarned(todayRecord.getPointsEarned())
                .signedToday(true)
                .balanceAfter(pointsFacade.getBalance(memberId))
                .build();
    }

    @Override
    public PageResult<SignInRecordVO> pageRecords(SignInRecordQuery query) {
        LambdaQueryWrapper<MemberSignIn> wrapper = new LambdaQueryWrapper<MemberSignIn>();
        if (query.getMemberId() != null) {
            wrapper.eq(MemberSignIn::getMemberId, query.getMemberId());
        }
        wrapper.orderByDesc(MemberSignIn::getSignDate);
        Page<MemberSignIn> page = memberSignInMapper.selectPage(
                new Page<MemberSignIn>(query.current(), query.size()), wrapper);
        return PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(),
                signInConverter.toVOList(page.getRecords()));
    }
}
