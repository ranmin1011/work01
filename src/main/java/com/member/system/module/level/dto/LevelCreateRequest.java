package com.member.system.module.level.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 创建等级请求
 */
@Data
public class LevelCreateRequest {

    @NotBlank(message = "等级编码不能为空")
    private String levelCode;

    @NotBlank(message = "等级名称不能为空")
    private String levelName;

    @NotNull(message = "最低积分不能为空")
    private Integer minPoints;

    private BigDecimal discountRate;

    private String privileges;

    private Integer sortOrder;
}
