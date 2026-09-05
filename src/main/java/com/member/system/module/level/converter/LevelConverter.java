package com.member.system.module.level.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.system.module.level.dto.LevelPrivilegeVO;
import com.member.system.module.level.dto.MemberLevelVO;
import com.member.system.module.level.entity.MemberLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 等级对象转换
 */
@Component
public class LevelConverter {

    private final ObjectMapper objectMapper;

    public LevelConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public MemberLevelVO toVO(MemberLevel level) {
        if (level == null) {
            return null;
        }
        return MemberLevelVO.builder()
                .id(level.getId())
                .levelCode(level.getLevelCode())
                .levelName(level.getLevelName())
                .minPoints(level.getMinPoints())
                .discountRate(level.getDiscountRate())
                .privileges(level.getPrivileges())
                .privilegeList(parsePrivileges(level.getPrivileges()))
                .sortOrder(level.getSortOrder())
                .status(level.getStatus())
                .createdAt(level.getCreatedAt())
                .build();
    }

    public List<MemberLevelVO> toVOList(List<MemberLevel> levels) {
        if (levels == null || levels.isEmpty()) {
            return Collections.emptyList();
        }
        List<MemberLevelVO> list = new ArrayList<MemberLevelVO>(levels.size());
        for (MemberLevel level : levels) {
            list.add(toVO(level));
        }
        return list;
    }

    private List<LevelPrivilegeVO> parsePrivileges(String privilegesJson) {
        if (!StringUtils.hasText(privilegesJson)) {
            return Collections.emptyList();
        }
        try {
            if (privilegesJson.trim().startsWith("[")) {
                List<String> names = objectMapper.readValue(privilegesJson, new TypeReference<List<String>>() {
                });
                List<LevelPrivilegeVO> result = new ArrayList<LevelPrivilegeVO>();
                if (names != null) {
                    for (int i = 0; i < names.size(); i++) {
                        String name = names.get(i);
                        result.add(LevelPrivilegeVO.builder()
                                .code("P" + (i + 1))
                                .name(name)
                                .description(name)
                                .build());
                    }
                }
                return result;
            }
        } catch (Exception ignored) {
            // fallback below
        }
        return Collections.singletonList(LevelPrivilegeVO.builder()
                .code("RAW")
                .name(privilegesJson)
                .description(privilegesJson)
                .build());
    }
}
