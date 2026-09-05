package com.member.system.module.health.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 健康状态视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthStatusVO {

    private String status;
    private String service;
    private Long timestamp;
}
