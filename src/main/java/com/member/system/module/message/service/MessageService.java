package com.member.system.module.message.service;

import com.member.system.common.result.PageResult;
import com.member.system.module.message.dto.MemberMessageQuery;
import com.member.system.module.message.dto.MemberMessageVO;
import com.member.system.module.message.dto.MessageSendCommand;

/**
 * 站内消息服务
 */
public interface MessageService {

    void send(MessageSendCommand command);

    PageResult<MemberMessageVO> pageMessages(MemberMessageQuery query);

    MemberMessageVO markRead(Long memberId, Long messageId);

    long unreadCount(Long memberId);
}
