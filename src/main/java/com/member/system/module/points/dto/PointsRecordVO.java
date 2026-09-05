package com.member.system.module.points.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 积分流水视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsRecordVO {

    private Long id;
    private Long memberId;
    private String changeType;
    private String changeTypeDesc;
    private Integer changeAmount;
    private Integer balanceAfter;
    private String bizNo;
    private String remark;
    private LocalDateTime createdAt;
}
