package com.member.system.module.address.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地址查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberAddressQuery extends PageQuery {

    private Long memberId;
}
