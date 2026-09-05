package com.member.system.module.event.listener;

import com.member.system.common.enums.MessageType;
import com.member.system.module.event.LevelUpgradedEvent;
import com.member.system.module.event.MemberRegisteredEvent;
import com.member.system.module.event.PointsChangedEvent;
import com.member.system.module.message.dto.MessageSendCommand;
import com.member.system.module.message.service.MessageService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 领域事件监听：写入站内消息
 */
@Component
public class MemberDomainEventListener {

    private final MessageService messageService;

    public MemberDomainEventListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @EventListener
    public void onRegistered(MemberRegisteredEvent event) {
        messageService.send(MessageSendCommand.builder()
                .memberId(event.getMemberId())
                .messageType(MessageType.SYSTEM.getCode())
                .title("欢迎加入会员")
                .content("尊敬的 " + event.getUsername() + "，欢迎注册成功，会员编号：" + event.getMemberNo())
                .build());
    }

    @EventListener
    public void onPointsChanged(PointsChangedEvent event) {
        String direction = event.getChangeAmount() >= 0 ? "增加" : "扣减";
        messageService.send(MessageSendCommand.builder()
                .memberId(event.getMemberId())
                .messageType(MessageType.POINTS.getCode())
                .title("积分变动通知")
                .content("您的积分已" + direction + Math.abs(event.getChangeAmount())
                        + "，当前余额 " + event.getBalanceAfter()
                        + "。" + (event.getRemark() == null ? "" : event.getRemark()))
                .build());
    }

    @EventListener
    public void onLevelUpgraded(LevelUpgradedEvent event) {
        messageService.send(MessageSendCommand.builder()
                .memberId(event.getMemberId())
                .messageType(MessageType.LEVEL.getCode())
                .title("等级升级通知")
                .content("恭喜您升级为「" + event.getToLevelName() + "」！")
                .build());
    }
}
