package com.member.system.module.auth.service;

import com.member.system.module.auth.dto.LoginResponse;
import com.member.system.module.auth.dto.MemberLoginRequest;
import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * 认证门面服务
 */
@Service
public class AuthService {

    private final MemberService memberService;

    public AuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberVO register(MemberRegisterRequest request) {
        return memberService.register(request);
    }

    public LoginResponse login(MemberLoginRequest request) {
        return memberService.login(request);
    }
}
