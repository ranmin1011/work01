package com.member.system.module.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送消息命令
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendCommand {

    private Long memberId;
    private String messageType;
    private String title;
    private String content;
}
