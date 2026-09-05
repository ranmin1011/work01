package com.member.system.module.auth.dto;

import com.member.system.common.constant.MemberConstants;
import com.member.system.common.security.PasswordStrength;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 会员注册请求
 */
@Data
public class MemberRegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = MemberConstants.USERNAME_MIN_LENGTH, max = MemberConstants.USERNAME_MAX_LENGTH,
            message = "用户名长度为4-32位")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名仅支持字母、数字和下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @PasswordStrength
    private String password;

    @Size(max = MemberConstants.NICKNAME_MAX_LENGTH, message = "昵称最长64字符")
    private String nickname;

    @Pattern(regexp = "^$|^1\\d{10}$", message = "手机号格式不正确")
    private String mobile;

    private String email;
    private String registerSource;
}
