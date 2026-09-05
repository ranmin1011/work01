package com.member.system.module.level.controller;

import com.member.system.common.result.ApiResult;
import com.member.system.module.level.dto.LevelCreateRequest;
import com.member.system.module.level.dto.MemberLevelQuery;
import com.member.system.module.level.dto.MemberLevelVO;
import com.member.system.module.level.entity.MemberLevel;
import com.member.system.module.level.service.MemberLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理端等级接口
 */
@Tag(name = "管理端-等级")
@RestController
@RequestMapping("/admin/levels")
public class AdminLevelController {

    private final MemberLevelService memberLevelService;

    public AdminLevelController(MemberLevelService memberLevelService) {
        this.memberLevelService = memberLevelService;
    }

    @Operation(summary = "等级列表（管理）")
    @GetMapping
    public ApiResult<List<MemberLevelVO>> list(MemberLevelQuery query) {
        return ApiResult.ok(memberLevelService.queryLevels(query));
    }

    @Operation(summary = "创建等级")
    @PostMapping
    public ApiResult<MemberLevelVO> create(@Validated @RequestBody LevelCreateRequest request) {
        MemberLevel level = new MemberLevel();
        level.setLevelCode(request.getLevelCode());
        level.setLevelName(request.getLevelName());
        level.setMinPoints(request.getMinPoints());
        level.setDiscountRate(request.getDiscountRate());
        level.setPrivileges(request.getPrivileges());
        level.setSortOrder(request.getSortOrder());
        return ApiResult.ok(memberLevelService.createLevel(level), "创建成功");
    }

    @Operation(summary = "启用/禁用等级")
    @PutMapping("/{id}/status")
    public ApiResult<Void> enable(@PathVariable("id") Long id,
                                  @RequestParam("enabled") boolean enabled) {
        memberLevelService.enableLevel(id, enabled);
        return ApiResult.ok(null, enabled ? "已启用" : "已禁用");
    }
}
