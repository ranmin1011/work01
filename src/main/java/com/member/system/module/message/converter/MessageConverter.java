package com.member.system.module.message.converter;

import com.member.system.common.enums.MessageType;
import com.member.system.module.message.dto.MemberMessageVO;
import com.member.system.module.message.entity.MemberMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 消息转换
 */
@Component
public class MessageConverter {

    public MemberMessageVO toVO(MemberMessage message) {
        if (message == null) {
            return null;
        }
        MessageType type = MessageType.of(message.getMessageType());
        return MemberMessageVO.builder()
                .id(message.getId())
                .memberId(message.getMemberId())
                .messageType(message.getMessageType())
                .messageTypeDesc(type == null ? message.getMessageType() : type.getDesc())
                .title(message.getTitle())
                .content(message.getContent())
                .readFlag(message.getReadFlag())
                .readAt(message.getReadAt())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public List<MemberMessageVO> toVOList(List<MemberMessage> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<MemberMessageVO> result = new ArrayList<MemberMessageVO>(list.size());
        for (MemberMessage item : list) {
            result.add(toVO(item));
        }
        return result;
    }
}
