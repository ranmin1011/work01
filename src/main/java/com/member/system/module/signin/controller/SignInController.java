package com.member.system.module.signin.controller;

import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.common.result.PageResult;
import com.member.system.module.signin.dto.SignInRecordQuery;
import com.member.system.module.signin.dto.SignInRecordVO;
import com.member.system.module.signin.dto.SignInResultVO;
import com.member.system.module.signin.service.SignInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签到接口
 */
@Tag(name = "签到")
@RestController
@RequestMapping("/sign-in")
public class SignInController {

    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    @Operation(summary = "今日签到")
    @PostMapping
    public ApiResult<SignInResultVO> signIn() {
        return ApiResult.ok(signInService.signIn(MemberContext.getMemberId()), "签到成功");
    }

    @Operation(summary = "今日签到状态")
    @GetMapping("/today")
    public ApiResult<SignInResultVO> today() {
        return ApiResult.ok(signInService.todayStatus(MemberContext.getMemberId()));
    }

    @Operation(summary = "签到记录")
    @GetMapping("/records")
    public ApiResult<PageResult<SignInRecordVO>> records(SignInRecordQuery query) {
        query.setMemberId(MemberContext.getMemberId());
        return ApiResult.ok(signInService.pageRecords(query));
    }
}
