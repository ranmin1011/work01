package com.member.system.module.member.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员分页查询条件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemberQuery extends PageQuery {

    private String username;
    private String mobile;
    private String nickname;
    private Integer status;
    private Long levelId;
}
