package com.member.system.module.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.result.PageResult;
import com.member.system.module.message.converter.MessageConverter;
import com.member.system.module.message.dto.MemberMessageQuery;
import com.member.system.module.message.dto.MemberMessageVO;
import com.member.system.module.message.dto.MessageSendCommand;
import com.member.system.module.message.entity.MemberMessage;
import com.member.system.module.message.mapper.MemberMessageMapper;
import com.member.system.module.message.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 站内消息服务实现
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MemberMessageMapper memberMessageMapper;
    private final MessageConverter messageConverter;

    public MessageServiceImpl(MemberMessageMapper memberMessageMapper, MessageConverter messageConverter) {
        this.memberMessageMapper = memberMessageMapper;
        this.messageConverter = messageConverter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(MessageSendCommand command) {
        BizAssert.notNull(command, ErrorCodes.MESSAGE_NOT_FOUND);
        BizAssert.notNull(command.getMemberId(), ErrorCodes.MEMBER_NOT_FOUND);
        BizAssert.notBlank(command.getTitle(), ErrorCodes.MESSAGE_NOT_FOUND);
        MemberMessage message = new MemberMessage();
        message.setMemberId(command.getMemberId());
        message.setMessageType(command.getMessageType());
        message.setTitle(command.getTitle());
        message.setContent(command.getContent());
        message.setReadFlag(0);
        memberMessageMapper.insert(message);
    }

    @Override
    public PageResult<MemberMessageVO> pageMessages(MemberMessageQuery query) {
        LambdaQueryWrapper<MemberMessage> wrapper = new LambdaQueryWrapper<MemberMessage>();
        if (query.getMemberId() != null) {
            wrapper.eq(MemberMessage::getMemberId, query.getMemberId());
        }
        if (StringUtils.hasText(query.getMessageType())) {
            wrapper.eq(MemberMessage::getMessageType, query.getMessageType());
        }
        if (query.getReadFlag() != null) {
            wrapper.eq(MemberMessage::getReadFlag, query.getReadFlag());
        }
        wrapper.orderByDesc(MemberMessage::getId);
        Page<MemberMessage> page = memberMessageMapper.selectPage(
                new Page<MemberMessage>(query.current(), query.size()), wrapper);
        return PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(),
                messageConverter.toVOList(page.getRecords()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberMessageVO markRead(Long memberId, Long messageId) {
        MemberMessage message = memberMessageMapper.selectById(messageId);
        BizAssert.notNull(message, ErrorCodes.MESSAGE_NOT_FOUND);
        BizAssert.isTrue(memberId.equals(message.getMemberId()), ErrorCodes.MESSAGE_NOT_FOUND);
        if (Integer.valueOf(1).equals(message.getReadFlag())) {
            return messageConverter.toVO(message);
        }
        message.setReadFlag(1);
        message.setReadAt(LocalDateTime.now());
        memberMessageMapper.updateById(message);
        return messageConverter.toVO(message);
    }

    @Override
    public long unreadCount(Long memberId) {
        Long count = memberMessageMapper.selectCount(new LambdaQueryWrapper<MemberMessage>()
                .eq(MemberMessage::getMemberId, memberId)
                .eq(MemberMessage::getReadFlag, 0));
        return count == null ? 0L : count;
    }
}
