package com.member.system.module.member.service;

import com.member.system.common.result.PageResult;
import com.member.system.module.member.dto.AdminMemberQuery;
import com.member.system.module.member.dto.AdminMemberVO;

/**
 * 管理端会员服务
 */
public interface AdminMemberService {

    PageResult<AdminMemberVO> pageMembers(AdminMemberQuery query);

    void enable(Long memberId);

    void disable(Long memberId);
}
