package com.member.system.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.system.common.constant.HeaderConstants;
import com.member.system.common.result.ApiResult;
import com.member.system.common.result.ResultCode;
import com.member.system.config.MemberProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * JWT 鉴权拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final MemberProperties memberProperties;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(JwtUtil jwtUtil, MemberProperties memberProperties, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.memberProperties = memberProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String authorization = request.getHeader(HeaderConstants.AUTHORIZATION);
        String prefix = memberProperties.getJwt().getTokenPrefix();
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(prefix)) {
            writeUnauthorized(response);
            return false;
        }
        String token = authorization.substring(prefix.length()).trim();
        try {
            if (jwtUtil.isExpired(token)) {
                writeUnauthorized(response);
                return false;
            }
            MemberContext.set(jwtUtil.parseJwtClaims(token));
            return true;
        } catch (Exception e) {
            writeUnauthorized(response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        MemberContext.clear();
    }

    private void writeUnauthorized(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(ApiResult.fail(ResultCode.UNAUTHORIZED)));
    }
}
