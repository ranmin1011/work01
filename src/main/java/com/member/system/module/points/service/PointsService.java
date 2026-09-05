package com.member.system.module.points.service;

import com.member.system.common.enums.PointsChangeType;
import com.member.system.common.result.PageResult;
import com.member.system.module.points.dto.PointsRecordQuery;
import com.member.system.module.points.dto.PointsRecordVO;
import com.member.system.module.points.entity.PointsRecord;

/**
 * 积分核心领域服务
 */
public interface PointsService {

    /**
     * 变更积分：校验余额、写流水、更新会员积分
     *
     * @param memberId    会员ID
     * @param amount      变动量，正数为增加，负数为扣减
     * @param changeType  变动类型
     * @param bizNo       业务单号
     * @param remark      备注
     * @return 流水记录
     */
    PointsRecord changePoints(Long memberId, int amount, PointsChangeType changeType, String bizNo, String remark);

    int getBalance(Long memberId);

    PageResult<PointsRecordVO> pageRecords(PointsRecordQuery query);
}
