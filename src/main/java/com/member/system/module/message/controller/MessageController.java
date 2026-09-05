package com.member.system.module.message.controller;

import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.common.result.PageResult;
import com.member.system.module.message.dto.MemberMessageQuery;
import com.member.system.module.message.dto.MemberMessageVO;
import com.member.system.module.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 站内消息接口
 */
@Tag(name = "站内消息")
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "我的消息")
    @GetMapping
    public ApiResult<PageResult<MemberMessageVO>> page(MemberMessageQuery query) {
        query.setMemberId(MemberContext.getMemberId());
        return ApiResult.ok(messageService.pageMessages(query));
    }

    @Operation(summary = "未读数量")
    @GetMapping("/unread-count")
    public ApiResult<Map<String, Long>> unreadCount() {
        Map<String, Long> data = new HashMap<String, Long>();
        data.put("count", messageService.unreadCount(MemberContext.getMemberId()));
        return ApiResult.ok(data);
    }

    @Operation(summary = "标记已读")
    @PutMapping("/{id}/read")
    public ApiResult<MemberMessageVO> markRead(@PathVariable("id") Long id) {
        return ApiResult.ok(messageService.markRead(MemberContext.getMemberId(), id), "已读");
    }
}
