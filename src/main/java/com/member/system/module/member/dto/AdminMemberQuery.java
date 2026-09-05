package com.member.system.module.member.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理端会员查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminMemberQuery extends PageQuery {

    private String username;
    private String mobile;
    private String memberNo;
    private String nickname;
    private Integer status;
    private Long levelId;
}
