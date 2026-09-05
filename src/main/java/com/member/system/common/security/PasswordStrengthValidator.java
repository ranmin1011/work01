package com.member.system.common.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 密码强度校验器
 */
public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

    private static final Pattern PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{6,32}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return PATTERN.matcher(value).matches();
    }
}
