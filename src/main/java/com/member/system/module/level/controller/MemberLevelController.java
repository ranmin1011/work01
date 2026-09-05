package com.member.system.module.level.controller;

import com.member.system.common.result.ApiResult;
import com.member.system.module.level.dto.MemberLevelVO;
import com.member.system.module.level.service.MemberLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公开等级查询接口
 */
@Tag(name = "会员等级")
@RestController
@RequestMapping("/levels")
public class MemberLevelController {

    private final MemberLevelService memberLevelService;

    public MemberLevelController(MemberLevelService memberLevelService) {
        this.memberLevelService = memberLevelService;
    }

    @Operation(summary = "启用中的等级列表")
    @GetMapping
    public ApiResult<List<MemberLevelVO>> list() {
        return ApiResult.ok(memberLevelService.listEnabledLevelVOs());
    }

    @Operation(summary = "等级详情")
    @GetMapping("/{id}")
    public ApiResult<MemberLevelVO> detail(@PathVariable("id") Long id) {
        return ApiResult.ok(memberLevelService.getVOById(id));
    }
}
