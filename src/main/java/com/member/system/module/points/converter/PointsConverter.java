package com.member.system.module.points.converter;

import com.member.system.common.enums.PointsChangeType;
import com.member.system.module.points.dto.PointsRecordVO;
import com.member.system.module.points.entity.PointsRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 积分对象转换
 */
@Component
public class PointsConverter {

    public PointsRecordVO toVO(PointsRecord record) {
        if (record == null) {
            return null;
        }
        PointsChangeType type = PointsChangeType.of(record.getChangeType());
        return PointsRecordVO.builder()
                .id(record.getId())
                .memberId(record.getMemberId())
                .changeType(record.getChangeType())
                .changeTypeDesc(type == null ? record.getChangeType() : type.getDesc())
                .changeAmount(record.getChangeAmount())
                .balanceAfter(record.getBalanceAfter())
                .bizNo(record.getBizNo())
                .remark(record.getRemark())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public List<PointsRecordVO> toVOList(List<PointsRecord> records) {
        if (records == null || records.isEmpty()) {
            return Collections.emptyList();
        }
        List<PointsRecordVO> list = new ArrayList<PointsRecordVO>(records.size());
        for (PointsRecord record : records) {
            list.add(toVO(record));
        }
        return list;
    }
}
