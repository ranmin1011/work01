package com.member.system.module.auth.controller;

import com.member.system.common.result.ApiResult;
import com.member.system.module.auth.dto.LoginResponse;
import com.member.system.module.auth.dto.MemberLoginRequest;
import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.auth.service.AuthService;
import com.member.system.module.member.dto.MemberVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证接口
 */
@Tag(name = "认证")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "会员注册")
    @PostMapping("/register")
    public ApiResult<MemberVO> register(@Validated @RequestBody MemberRegisterRequest request) {
        return ApiResult.ok(authService.register(request), "注册成功");
    }

    @Operation(summary = "会员登录")
    @PostMapping("/login")
    public ApiResult<LoginResponse> login(@Validated @RequestBody MemberLoginRequest request) {
        return ApiResult.ok(authService.login(request), "登录成功");
    }
}
