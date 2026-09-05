package com.member.system.module.points.dto;

import com.member.system.common.enums.PointsChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 积分发放命令
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGrantCommand {

    private Long memberId;
    private Integer amount;
    private PointsChangeType changeType;
    private String bizNo;
    private String remark;
}
