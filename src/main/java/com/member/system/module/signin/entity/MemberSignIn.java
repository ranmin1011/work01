package com.member.system.module.signin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员签到记录
 */
@Data
@TableName("member_sign_in")
public class MemberSignIn {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;
    private LocalDate signDate;
    private Integer continuousDays;
    private Integer pointsEarned;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
