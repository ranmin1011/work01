package com.member.system.module.operlog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 */
@Data
@TableName("oper_log")
public class OperLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long memberId;
    private String module;
    private String operation;
    private String method;
    private String requestUri;
    private String requestParams;
    private String ip;
    private Integer success;
    private String errorMsg;
    private Long costMs;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
