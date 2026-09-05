package com.member.system.module.points.facade.impl;

import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.util.BizNoGenerator;
import com.member.system.module.points.dto.PointsGrantCommand;
import com.member.system.module.points.facade.PointsFacade;
import com.member.system.module.points.service.PointsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 积分门面实现，委托 PointsService
 */
@Service
public class PointsFacadeImpl implements PointsFacade {

    private final PointsService pointsService;

    public PointsFacadeImpl(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @Override
    public void grant(PointsGrantCommand command) {
        BizAssert.notNull(command, ErrorCodes.POINTS_CHANGE_INVALID);
        BizAssert.notNull(command.getMemberId(), ErrorCodes.MEMBER_NOT_FOUND);
        BizAssert.isTrue(command.getAmount() != null && command.getAmount() > 0, ErrorCodes.POINTS_CHANGE_INVALID);
        BizAssert.notNull(command.getChangeType(), ErrorCodes.POINTS_CHANGE_INVALID);

        String bizNo = StringUtils.hasText(command.getBizNo())
                ? command.getBizNo()
                : BizNoGenerator.pointsBizNo("PG");
        pointsService.changePoints(command.getMemberId(), command.getAmount(),
                command.getChangeType(), bizNo, command.getRemark());
    }

    @Override
    public int getBalance(Long memberId) {
        return pointsService.getBalance(memberId);
    }
}
