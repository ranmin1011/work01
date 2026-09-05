package com.member.system.module.signin.converter;

import com.member.system.module.signin.dto.SignInRecordVO;
import com.member.system.module.signin.entity.MemberSignIn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 签到对象转换
 */
@Component
public class SignInConverter {

    public SignInRecordVO toVO(MemberSignIn entity) {
        if (entity == null) {
            return null;
        }
        return SignInRecordVO.builder()
                .id(entity.getId())
                .memberId(entity.getMemberId())
                .signDate(entity.getSignDate())
                .continuousDays(entity.getContinuousDays())
                .pointsEarned(entity.getPointsEarned())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<SignInRecordVO> toVOList(List<MemberSignIn> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<SignInRecordVO> result = new ArrayList<SignInRecordVO>(list.size());
        for (MemberSignIn item : list) {
            result.add(toVO(item));
        }
        return result;
    }
}
