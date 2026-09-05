package com.member.system.module.member.controller;

import com.member.system.common.annotation.OperLog;
import com.member.system.common.result.ApiResult;
import com.member.system.common.result.PageResult;
import com.member.system.module.member.dto.AdminMemberQuery;
import com.member.system.module.member.dto.AdminMemberVO;
import com.member.system.module.member.service.AdminMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端会员接口
 */
@Tag(name = "管理端-会员")
@RestController
@RequestMapping("/admin/members")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    public AdminMemberController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    @Operation(summary = "会员分页查询")
    @GetMapping
    public ApiResult<PageResult<AdminMemberVO>> page(AdminMemberQuery query) {
        return ApiResult.ok(adminMemberService.pageMembers(query));
    }

    @Operation(summary = "启用会员")
    @OperLog(module = "管理端会员", value = "启用会员")
    @PutMapping("/{id}/enable")
    public ApiResult<Void> enable(@PathVariable("id") Long id) {
        adminMemberService.enable(id);
        return ApiResult.ok(null, "已启用");
    }

    @Operation(summary = "禁用会员")
    @OperLog(module = "管理端会员", value = "禁用会员")
    @PutMapping("/{id}/disable")
    public ApiResult<Void> disable(@PathVariable("id") Long id) {
        adminMemberService.disable(id);
        return ApiResult.ok(null, "已禁用");
    }
}
