package com.member.system.module.health.controller;

import com.member.system.common.result.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查
 */
@Tag(name = "系统")
@RestController
public class HealthController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public ApiResult<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("status", "UP");
        data.put("service", "member-system");
        data.put("timestamp", System.currentTimeMillis());
        return ApiResult.ok(data);
    }
}
