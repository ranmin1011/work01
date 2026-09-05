package com.member.system.module.member.service;

import com.member.system.module.auth.dto.LoginResponse;
import com.member.system.module.auth.dto.MemberLoginRequest;
import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.member.dto.MemberProfileUpdateRequest;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.entity.Member;

/**
 * 会员服务
 */
public interface MemberService {

    MemberVO register(MemberRegisterRequest request);

    LoginResponse login(MemberLoginRequest request);

    MemberVO getMemberVO(Long memberId);

    MemberVO updateProfile(Long memberId, MemberProfileUpdateRequest request);

    Member requireMember(Long memberId);

    void refreshLevel(Long memberId);
}
