package com.member.system.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员简要信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSimpleVO {

    private Long id;
    private String memberNo;
    private String username;
    private String nickname;
    private String avatar;
    private Long levelId;
    private Integer points;
}
