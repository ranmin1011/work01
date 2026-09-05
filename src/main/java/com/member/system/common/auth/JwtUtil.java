package com.member.system.common.auth;

import com.member.system.config.MemberProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具
 */
@Component
public class JwtUtil {

    private final MemberProperties memberProperties;
    private final SecretKey secretKey;

    public JwtUtil(MemberProperties memberProperties) {
        this.memberProperties = memberProperties;
        byte[] keyBytes = memberProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8);
        byte[] padded = new byte[Math.max(keyBytes.length, 32)];
        System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, padded.length));
        this.secretKey = Keys.hmacShaKeyFor(padded);
    }

    public String generateToken(JwtClaims jwtClaims) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("memberId", jwtClaims.getMemberId());
        claims.put("username", jwtClaims.getUsername());
        claims.put("memberNo", jwtClaims.getMemberNo());
        long expireMillis = memberProperties.getJwt().getExpireHours() * 3600L * 1000L;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(jwtClaims.getMemberId()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenInfo createTokenInfo(JwtClaims jwtClaims) {
        String token = generateToken(jwtClaims);
        int hours = memberProperties.getJwt().getExpireHours();
        return TokenInfo.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expireHours(hours)
                .expireAt(LocalDateTime.now().plusHours(hours))
                .build();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtClaims parseJwtClaims(String token) {
        Claims claims = parseClaims(token);
        Long memberId = toLong(claims.get("memberId"));
        if (memberId == null) {
            memberId = Long.valueOf(claims.getSubject());
        }
        return JwtClaims.builder()
                .memberId(memberId)
                .username((String) claims.get("username"))
                .memberNo((String) claims.get("memberNo"))
                .build();
    }

    public boolean isExpired(String token) {
        try {
            return parseClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        return Long.valueOf(String.valueOf(value));
    }
}
