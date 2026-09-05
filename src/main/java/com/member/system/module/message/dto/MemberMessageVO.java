package com.member.system.module.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消息视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberMessageVO {

    private Long id;
    private Long memberId;
    private String messageType;
    private String messageTypeDesc;
    private String title;
    private String content;
    private Integer readFlag;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
}
