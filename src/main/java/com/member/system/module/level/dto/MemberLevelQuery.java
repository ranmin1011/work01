package com.member.system.module.level.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员等级查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberLevelQuery extends PageQuery {

    private String levelCode;
    private String levelName;
    private Integer status;
}
