package com.member.system.module.points.controller;

import com.member.system.common.result.ApiResult;
import com.member.system.common.result.PageResult;
import com.member.system.module.points.dto.PointsAdjustRequest;
import com.member.system.module.points.dto.PointsRecordQuery;
import com.member.system.module.points.dto.PointsRecordVO;
import com.member.system.module.points.dto.PointsRewardRequest;
import com.member.system.module.points.service.PointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端积分接口
 */
@Tag(name = "管理端-积分")
@RestController
@RequestMapping("/admin/points")
public class AdminPointsController {

    private final PointsService pointsService;

    public AdminPointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @Operation(summary = "积分流水查询")
    @GetMapping("/records")
    public ApiResult<PageResult<PointsRecordVO>> records(PointsRecordQuery query) {
        return ApiResult.ok(pointsService.pageRecords(query));
    }

    @Operation(summary = "人工调整积分")
    @PostMapping("/adjust")
    public ApiResult<PointsRecordVO> adjust(@Validated @RequestBody PointsAdjustRequest request) {
        return ApiResult.ok(pointsService.adjust(request), "调整成功");
    }

    @Operation(summary = "发放奖励积分")
    @PostMapping("/reward")
    public ApiResult<PointsRecordVO> reward(@Validated @RequestBody PointsRewardRequest request) {
        return ApiResult.ok(pointsService.reward(request), "发放成功");
    }
}
