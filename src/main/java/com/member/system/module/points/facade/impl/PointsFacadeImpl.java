package com.member.system.module.points.facade.impl;

import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.util.BizNoGenerator;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import com.member.system.module.points.dto.PointsGrantCommand;
import com.member.system.module.points.facade.PointsFacade;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 积分门面最小实现（步骤22将委托 PointsService）
 */
@Service
public class PointsFacadeImpl implements PointsFacade {

    private final MemberMapper memberMapper;

    public PointsFacadeImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void grant(PointsGrantCommand command) {
        BizAssert.notNull(command, ErrorCodes.POINTS_CHANGE_INVALID);
        BizAssert.notNull(command.getMemberId(), ErrorCodes.MEMBER_NOT_FOUND);
        BizAssert.isTrue(command.getAmount() != null && command.getAmount() > 0, ErrorCodes.POINTS_CHANGE_INVALID);

        Member member = memberMapper.selectById(command.getMemberId());
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);

        int amount = command.getAmount();
        int points = member.getPoints() == null ? 0 : member.getPoints();
        int totalPoints = member.getTotalPoints() == null ? 0 : member.getTotalPoints();
        member.setPoints(points + amount);
        member.setTotalPoints(totalPoints + amount);
        memberMapper.updateById(member);
        // 流水写入在步骤22完善；此处保留 bizNo 占位逻辑
        if (!StringUtils.hasText(command.getBizNo())) {
            command.setBizNo(BizNoGenerator.pointsBizNo("PG"));
        }
    }

    @Override
    public int getBalance(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        return member.getPoints() == null ? 0 : member.getPoints();
    }
}
