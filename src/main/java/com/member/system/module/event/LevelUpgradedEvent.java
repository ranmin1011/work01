package com.member.system.module.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 等级升级事件
 */
@Getter
public class LevelUpgradedEvent extends ApplicationEvent {

    private final Long memberId;
    private final Long fromLevelId;
    private final Long toLevelId;
    private final String toLevelName;

    public LevelUpgradedEvent(Object source, Long memberId, Long fromLevelId,
                              Long toLevelId, String toLevelName) {
        super(source);
        this.memberId = memberId;
        this.fromLevelId = fromLevelId;
        this.toLevelId = toLevelId;
        this.toLevelName = toLevelName;
    }
}
