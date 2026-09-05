package com.member.system.module.operlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperLogVO {

    private Long id;
    private Long memberId;
    private String module;
    private String operation;
    private String method;
    private String requestUri;
    private String requestParams;
    private String ip;
    private Integer success;
    private String errorMsg;
    private Long costMs;
    private LocalDateTime createdAt;
}
