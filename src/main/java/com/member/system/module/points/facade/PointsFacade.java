package com.member.system.module.points.facade;

import com.member.system.module.points.dto.PointsGrantCommand;

/**
 * 积分门面：对外部模块暴露简化积分能力
 */
public interface PointsFacade {

    /**
     * 发放积分（注册奖励、签到等）
     */
    void grant(PointsGrantCommand command);

    /**
     * 查询当前可用积分余额
     */
    int getBalance(Long memberId);
}
