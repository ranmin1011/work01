package com.member.system.module.member.dto;

import com.member.system.common.constant.MemberConstants;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 会员资料更新请求
 */
@Data
public class MemberProfileUpdateRequest {

    @Size(max = MemberConstants.NICKNAME_MAX_LENGTH, message = "昵称最长64字符")
    private String nickname;

    private String email;
    private String avatar;

    @Min(value = 0, message = "性别取值无效")
    @Max(value = 2, message = "性别取值无效")
    private Integer gender;

    private LocalDate birthday;
}
