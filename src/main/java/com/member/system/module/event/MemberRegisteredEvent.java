package com.member.system.module.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 会员注册成功事件
 */
@Getter
public class MemberRegisteredEvent extends ApplicationEvent {

    private final Long memberId;
    private final String username;
    private final String memberNo;

    public MemberRegisteredEvent(Object source, Long memberId, String username, String memberNo) {
        super(source);
        this.memberId = memberId;
        this.username = username;
        this.memberNo = memberNo;
    }
}
