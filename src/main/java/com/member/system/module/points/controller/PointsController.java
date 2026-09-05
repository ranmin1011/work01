package com.member.system.module.points.controller;

import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.common.result.PageResult;
import com.member.system.module.points.dto.PointsBalanceVO;
import com.member.system.module.points.dto.PointsConsumeRequest;
import com.member.system.module.points.dto.PointsRecordQuery;
import com.member.system.module.points.dto.PointsRecordVO;
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
 * 会员积分查询接口
 */
@Tag(name = "积分")
@RestController
@RequestMapping("/points")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @Operation(summary = "当前积分余额")
    @GetMapping("/balance")
    public ApiResult<PointsBalanceVO> balance() {
        Long memberId = MemberContext.getMemberId();
        return ApiResult.ok(PointsBalanceVO.builder()
                .memberId(memberId)
                .points(pointsService.getBalance(memberId))
                .build());
    }

    @Operation(summary = "我的积分流水")
    @GetMapping("/records")
    public ApiResult<PageResult<PointsRecordVO>> records(PointsRecordQuery query) {
        query.setMemberId(MemberContext.getMemberId());
        return ApiResult.ok(pointsService.pageRecords(query));
    }

    @Operation(summary = "消费积分")
    @PostMapping("/consume")
    public ApiResult<PointsRecordVO> consume(@Validated @RequestBody PointsConsumeRequest request) {
        return ApiResult.ok(pointsService.consume(MemberContext.getMemberId(), request), "消费成功");
    }
}
