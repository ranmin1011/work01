package com.member.system.module.level.service;

import com.member.system.module.level.dto.MemberLevelQuery;
import com.member.system.module.level.dto.MemberLevelVO;
import com.member.system.module.level.entity.MemberLevel;

import java.util.List;

/**
 * 会员等级服务
 */
public interface MemberLevelService {

    List<MemberLevel> listEnabledLevels();

    List<MemberLevelVO> listEnabledLevelVOs();

    MemberLevel getById(Long levelId);

    MemberLevelVO getVOById(Long levelId);

    MemberLevel matchLevelByTotalPoints(int totalPoints);

    void refreshMemberLevel(Long memberId);

    MemberLevelVO createLevel(MemberLevel level);

    void enableLevel(Long levelId, boolean enabled);

    List<MemberLevelVO> queryLevels(MemberLevelQuery query);
}
