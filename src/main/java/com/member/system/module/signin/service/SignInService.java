package com.member.system.module.signin.service;

import com.member.system.common.result.PageResult;
import com.member.system.module.signin.dto.SignInRecordQuery;
import com.member.system.module.signin.dto.SignInRecordVO;
import com.member.system.module.signin.dto.SignInResultVO;

/**
 * 签到领域服务
 */
public interface SignInService {

    SignInResultVO signIn(Long memberId);

    SignInResultVO todayStatus(Long memberId);

    PageResult<SignInRecordVO> pageRecords(SignInRecordQuery query);
}
