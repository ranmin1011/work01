package com.member.system.module.address.controller;

import com.member.system.common.annotation.OperLog;
import com.member.system.common.auth.MemberContext;
import com.member.system.common.result.ApiResult;
import com.member.system.module.address.dto.MemberAddressRequest;
import com.member.system.module.address.dto.MemberAddressVO;
import com.member.system.module.address.dto.SetDefaultAddressRequest;
import com.member.system.module.address.service.MemberAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员地址接口
 */
@Tag(name = "会员地址")
@RestController
@RequestMapping("/addresses")
public class MemberAddressController {

    private final MemberAddressService memberAddressService;

    public MemberAddressController(MemberAddressService memberAddressService) {
        this.memberAddressService = memberAddressService;
    }

    @Operation(summary = "地址列表")
    @GetMapping
    public ApiResult<List<MemberAddressVO>> list() {
        return ApiResult.ok(memberAddressService.listByMember(MemberContext.getMemberId()));
    }

    @Operation(summary = "默认地址")
    @GetMapping("/default")
    public ApiResult<MemberAddressVO> getDefault() {
        return ApiResult.ok(memberAddressService.getDefault(MemberContext.getMemberId()));
    }

    @Operation(summary = "新增地址")
    @OperLog(module = "地址", value = "新增地址")
    @PostMapping
    public ApiResult<MemberAddressVO> create(@Validated @RequestBody MemberAddressRequest request) {
        return ApiResult.ok(memberAddressService.create(MemberContext.getMemberId(), request), "创建成功");
    }

    @Operation(summary = "更新地址")
    @OperLog(module = "地址", value = "更新地址")
    @PutMapping("/{id}")
    public ApiResult<MemberAddressVO> update(@PathVariable("id") Long id,
                                             @Validated @RequestBody MemberAddressRequest request) {
        return ApiResult.ok(memberAddressService.update(MemberContext.getMemberId(), id, request), "更新成功");
    }

    @Operation(summary = "删除地址")
    @OperLog(module = "地址", value = "删除地址")
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable("id") Long id) {
        memberAddressService.delete(MemberContext.getMemberId(), id);
        return ApiResult.ok(null, "删除成功");
    }

    @Operation(summary = "设为默认地址")
    @PutMapping("/default")
    public ApiResult<Void> setDefault(@Validated @RequestBody SetDefaultAddressRequest request) {
        memberAddressService.setDefault(MemberContext.getMemberId(), request.getAddressId());
        return ApiResult.ok(null, "设置成功");
    }
}
