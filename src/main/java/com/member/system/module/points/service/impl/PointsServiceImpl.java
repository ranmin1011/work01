package com.member.system.module.points.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.member.system.common.enums.PointsChangeType;
import com.member.system.common.exception.BizAssert;
import com.member.system.common.exception.ErrorCodes;
import com.member.system.common.result.PageResult;
import com.member.system.common.util.BizNoGenerator;
import com.member.system.module.member.entity.Member;
import com.member.system.module.member.mapper.MemberMapper;
import com.member.system.module.member.service.MemberService;
import com.member.system.module.points.converter.PointsConverter;
import com.member.system.module.points.dto.PointsRecordQuery;
import com.member.system.module.points.dto.PointsRecordVO;
import com.member.system.module.points.entity.PointsRecord;
import com.member.system.module.points.mapper.PointsRecordMapper;
import com.member.system.module.points.service.PointsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 积分核心领域服务实现
 */
@Service
public class PointsServiceImpl implements PointsService {

    private final PointsRecordMapper pointsRecordMapper;
    private final MemberMapper memberMapper;
    private final PointsConverter pointsConverter;
    private final MemberService memberService;

    public PointsServiceImpl(PointsRecordMapper pointsRecordMapper,
                             MemberMapper memberMapper,
                             PointsConverter pointsConverter,
                             @Lazy MemberService memberService) {
        this.pointsRecordMapper = pointsRecordMapper;
        this.memberMapper = memberMapper;
        this.pointsConverter = pointsConverter;
        this.memberService = memberService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PointsRecord changePoints(Long memberId, int amount, PointsChangeType changeType, String bizNo, String remark) {
        BizAssert.notNull(memberId, ErrorCodes.MEMBER_NOT_FOUND);
        BizAssert.isTrue(amount != 0, ErrorCodes.POINTS_CHANGE_INVALID);
        BizAssert.notNull(changeType, ErrorCodes.POINTS_CHANGE_INVALID);

        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);

        int current = member.getPoints() == null ? 0 : member.getPoints();
        int total = member.getTotalPoints() == null ? 0 : member.getTotalPoints();
        int after = current + amount;
        BizAssert.isTrue(after >= 0, ErrorCodes.POINTS_INSUFFICIENT);

        member.setPoints(after);
        if (amount > 0) {
            member.setTotalPoints(total + amount);
        }
        memberMapper.updateById(member);

        PointsRecord record = new PointsRecord();
        record.setMemberId(memberId);
        record.setChangeType(changeType.getCode());
        record.setChangeAmount(amount);
        record.setBalanceAfter(after);
        record.setBizNo(StringUtils.hasText(bizNo) ? bizNo : BizNoGenerator.pointsBizNo("PT"));
        record.setRemark(remark);
        pointsRecordMapper.insert(record);

        memberService.refreshLevel(memberId);
        return record;
    }

    @Override
    public int getBalance(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        BizAssert.notNull(member, ErrorCodes.MEMBER_NOT_FOUND);
        return member.getPoints() == null ? 0 : member.getPoints();
    }

    @Override
    public PageResult<PointsRecordVO> pageRecords(PointsRecordQuery query) {
        BizAssert.notNull(query, ErrorCodes.POINTS_CHANGE_INVALID);
        LambdaQueryWrapper<PointsRecord> wrapper = new LambdaQueryWrapper<PointsRecord>();
        if (query.getMemberId() != null) {
            wrapper.eq(PointsRecord::getMemberId, query.getMemberId());
        }
        if (StringUtils.hasText(query.getChangeType())) {
            wrapper.eq(PointsRecord::getChangeType, query.getChangeType());
        }
        if (StringUtils.hasText(query.getBizNo())) {
            wrapper.eq(PointsRecord::getBizNo, query.getBizNo());
        }
        wrapper.orderByDesc(PointsRecord::getId);
        Page<PointsRecord> page = pointsRecordMapper.selectPage(
                new Page<PointsRecord>(query.current(), query.size()), wrapper);
        return PageResult.of(page.getCurrent(), page.getSize(), page.getTotal(),
                pointsConverter.toVOList(page.getRecords()));
    }
}
