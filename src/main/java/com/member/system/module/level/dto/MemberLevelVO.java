package com.member.system.module.level.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会员等级视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLevelVO {

    private Long id;
    private String levelCode;
    private String levelName;
    private Integer minPoints;
    private BigDecimal discountRate;
    private String privileges;
    private List<LevelPrivilegeVO> privilegeList;
    private Integer sortOrder;
    private Integer status;
    private LocalDateTime createdAt;
}
