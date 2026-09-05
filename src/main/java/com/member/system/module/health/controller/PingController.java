package com.member.system.module.health.controller;

import com.member.system.common.result.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 存活探测
 */
@Tag(name = "系统")
@RestController
public class PingController {

    @Operation(summary = "Ping")
    @GetMapping("/ping")
    public ApiResult<String> ping() {
        return ApiResult.ok("pong");
    }
}
