package com.member.system.module.level.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 等级权益项
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelPrivilegeVO {

    private String code;
    private String name;
    private String description;
}
