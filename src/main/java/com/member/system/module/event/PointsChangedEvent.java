package com.member.system.module.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 积分变更事件
 */
@Getter
public class PointsChangedEvent extends ApplicationEvent {

    private final Long memberId;
    private final Integer changeAmount;
    private final Integer balanceAfter;
    private final String changeType;
    private final String remark;

    public PointsChangedEvent(Object source, Long memberId, Integer changeAmount,
                              Integer balanceAfter, String changeType, String remark) {
        super(source);
        this.memberId = memberId;
        this.changeAmount = changeAmount;
        this.balanceAfter = balanceAfter;
        this.changeType = changeType;
        this.remark = remark;
    }
}
