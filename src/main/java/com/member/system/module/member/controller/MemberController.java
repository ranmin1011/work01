package com.member.system.module.member.controller;

import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.module.member.dto.MemberProfileUpdateRequest;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员资料接口
 */
@Tag(name = "会员")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "当前会员信息")
    @GetMapping("/me")
    public ApiResult<MemberVO> me() {
        return ApiResult.ok(memberService.getMemberVO(MemberContext.getMemberId()));
    }

    @Operation(summary = "更新当前会员资料")
    @PutMapping("/me")
    public ApiResult<MemberVO> updateProfile(@Validated @RequestBody MemberProfileUpdateRequest request) {
        return ApiResult.ok(memberService.updateProfile(MemberContext.getMemberId(), request), "更新成功");
    }
}
