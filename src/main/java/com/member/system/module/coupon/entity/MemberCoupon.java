package com.member.system.module.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 会员优惠券
 */
@Data
@TableName("member_coupon")
public class MemberCoupon {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;
    private Long couponId;
    private String couponCode;
    private Integer status;
    private LocalDateTime claimedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expireAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
