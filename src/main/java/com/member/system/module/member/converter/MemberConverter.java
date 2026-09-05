package com.member.system.module.member.converter;

import com.member.system.module.auth.dto.MemberRegisterRequest;
import com.member.system.module.member.dto.MemberDetailVO;
import com.member.system.module.member.dto.MemberSimpleVO;
import com.member.system.module.member.dto.MemberVO;
import com.member.system.module.member.entity.Member;
import com.member.system.module.level.entity.MemberLevel;
import org.springframework.stereotype.Component;

/**
 * 会员对象转换
 */
@Component
public class MemberConverter {

    public MemberVO toVO(Member member, MemberLevel level) {
        if (member == null) {
            return null;
        }
        MemberVO.MemberVOBuilder builder = MemberVO.builder()
                .id(member.getId())
                .memberNo(member.getMemberNo())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .mobile(member.getMobile())
                .email(member.getEmail())
                .avatar(member.getAvatar())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .status(member.getStatus())
                .levelId(member.getLevelId())
                .points(member.getPoints())
                .totalPoints(member.getTotalPoints())
                .lastLoginAt(member.getLastLoginAt())
                .createdAt(member.getCreatedAt());
        if (level != null) {
            builder.levelName(level.getLevelName()).levelCode(level.getLevelCode());
        }
        return builder.build();
    }

    public MemberDetailVO toDetailVO(Member member, MemberLevel level) {
        if (member == null) {
            return null;
        }
        MemberDetailVO.MemberDetailVOBuilder builder = MemberDetailVO.builder()
                .id(member.getId())
                .memberNo(member.getMemberNo())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .mobile(member.getMobile())
                .email(member.getEmail())
                .avatar(member.getAvatar())
                .gender(member.getGender())
                .birthday(member.getBirthday())
                .status(member.getStatus())
                .levelId(member.getLevelId())
                .points(member.getPoints())
                .totalPoints(member.getTotalPoints())
                .registerSource(member.getRegisterSource())
                .lastLoginAt(member.getLastLoginAt())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt());
        if (level != null) {
            builder.levelName(level.getLevelName()).levelCode(level.getLevelCode());
        }
        return builder.build();
    }

    public MemberSimpleVO toSimpleVO(Member member) {
        if (member == null) {
            return null;
        }
        return MemberSimpleVO.builder()
                .id(member.getId())
                .memberNo(member.getMemberNo())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .avatar(member.getAvatar())
                .levelId(member.getLevelId())
                .points(member.getPoints())
                .build();
    }

    public void applyRegister(Member member, MemberRegisterRequest request) {
        member.setUsername(request.getUsername());
        member.setNickname(request.getNickname());
        member.setMobile(request.getMobile());
        member.setEmail(request.getEmail());
        member.setRegisterSource(request.getRegisterSource());
    }
}
