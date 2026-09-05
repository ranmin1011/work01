package com.member.system.common.security;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 密码编码器（MD5 + 盐）。生产环境可替换为 BCrypt。
 */
@Component
public class PasswordEncoder {

    private static final String PEPPER = "member_system_pepper_v1";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String encode(String rawPassword) {
        String salt = randomSalt();
        return salt + "$" + hash(salt, rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null || !encodedPassword.contains("$")) {
            return false;
        }
        int idx = encodedPassword.indexOf('$');
        String salt = encodedPassword.substring(0, idx);
        String expect = encodedPassword.substring(idx + 1);
        return hash(salt, rawPassword).equals(expect);
    }

    private String hash(String salt, String rawPassword) {
        String raw = PEPPER + salt + rawPassword;
        return DigestUtils.md5DigestAsHex(raw.getBytes(StandardCharsets.UTF_8));
    }

    private String randomSalt() {
        byte[] bytes = new byte[8];
        RANDOM.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
