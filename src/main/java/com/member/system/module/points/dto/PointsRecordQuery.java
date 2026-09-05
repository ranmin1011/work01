package com.member.system.module.points.dto;

import com.member.system.common.result.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分流水分页查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PointsRecordQuery extends PageQuery {

    private Long memberId;
    private String changeType;
    private String bizNo;
}
