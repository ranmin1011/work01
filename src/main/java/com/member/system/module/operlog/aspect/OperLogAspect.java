package com.member.system.module.operlog.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.system.common.auth.MemberContext;
import com.member.system.module.operlog.mapper.OperLogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志切面
 */
@Aspect
@Component
public class OperLogAspect {

    private static final Logger log = LoggerFactory.getLogger(OperLogAspect.class);

    private final OperLogMapper operLogMapper;
    private final ObjectMapper objectMapper;

    public OperLogAspect(OperLogMapper operLogMapper, ObjectMapper objectMapper) {
        this.operLogMapper = operLogMapper;
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(operLogAnno)")
    public Object around(ProceedingJoinPoint joinPoint,
                         com.member.system.common.annotation.OperLog operLogAnno) throws Throwable {
        long start = System.currentTimeMillis();
        com.member.system.module.operlog.entity.OperLog entity =
                new com.member.system.module.operlog.entity.OperLog();
        entity.setModule(operLogAnno.module());
        entity.setOperation(operLogAnno.value());
        entity.setMemberId(MemberContext.getMemberId());
        entity.setSuccess(1);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        entity.setMethod(signature.getDeclaringTypeName() + "#" + signature.getName());

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            entity.setRequestUri(request.getRequestURI());
            entity.setIp(request.getRemoteAddr());
        }

        if (operLogAnno.recordParams()) {
            try {
                entity.setRequestParams(objectMapper.writeValueAsString(joinPoint.getArgs()));
            } catch (Exception e) {
                entity.setRequestParams("unserializable");
            }
        }

        try {
            Object result = joinPoint.proceed();
            entity.setCostMs(System.currentTimeMillis() - start);
            saveQuietly(entity);
            return result;
        } catch (Throwable ex) {
            entity.setSuccess(0);
            entity.setErrorMsg(ex.getMessage());
            entity.setCostMs(System.currentTimeMillis() - start);
            saveQuietly(entity);
            throw ex;
        }
    }

    private void saveQuietly(com.member.system.module.operlog.entity.OperLog entity) {
        try {
            operLogMapper.insert(entity);
        } catch (Exception e) {
            log.warn("保存操作日志失败: {}", e.getMessage());
        }
    }
}
