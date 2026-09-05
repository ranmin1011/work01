package com.member.system.module.operlog.converter;

import com.member.system.module.operlog.dto.OperLogVO;
import com.member.system.module.operlog.entity.OperLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 操作日志转换
 */
@Component
public class OperLogConverter {

    public OperLogVO toVO(OperLog log) {
        if (log == null) {
            return null;
        }
        return OperLogVO.builder()
                .id(log.getId())
                .memberId(log.getMemberId())
                .module(log.getModule())
                .operation(log.getOperation())
                .method(log.getMethod())
                .requestUri(log.getRequestUri())
                .requestParams(log.getRequestParams())
                .ip(log.getIp())
                .success(log.getSuccess())
                .errorMsg(log.getErrorMsg())
                .costMs(log.getCostMs())
                .createdAt(log.getCreatedAt())
                .build();
    }

    public List<OperLogVO> toVOList(List<OperLog> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<OperLogVO> result = new ArrayList<OperLogVO>(list.size());
        for (OperLog item : list) {
            result.add(toVO(item));
        }
        return result;
    }
}
