package com.member.system.module.message.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberMessageQuery extends PageQuery {

    private Long memberId;
    private String messageType;
    private Integer readFlag;
}
