package com.member.system.module.signin.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 签到记录查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SignInRecordQuery extends PageQuery {

    private Long memberId;
}
