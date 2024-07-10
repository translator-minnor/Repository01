package com.minnow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minnow.domain.SysRole;
import com.minnow.dto.SysRoleAddOrUpdateParam;
import com.minnow.dto.SysRoleQueryParam;
import com.minnow.model.Result;
import com.minnow.service.SysRoleService;
import com.minnow.vo.SysRoleDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 多条件分页查询角色列表
     * @param page
     * @param queryParam
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:role:page')")
    public Result<Page<SysRole>> loadSysRolePage(Page<SysRole> page, SysRoleQueryParam queryParam) {
        page = sysRoleService.page(page,new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.hasText(queryParam.getRoleName()),SysRole::getRoleName,queryParam.getRoleName())
                .orderByDesc(SysRole::getCreateTime)
        );
        return Result.success(page);
    }

    /**
     *新增角色以及对应权限
     * @param addOrUpdateParam
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result<String> saveSysRole(@RequestBody SysRoleAddOrUpdateParam addOrUpdateParam) {
        Integer count = sysRoleService.addSysRole(addOrUpdateParam);
        return Result.handle(count>0);
    }

    /**
     * 查询角色详情(包含权限ID集合)
     * @param roleId
     * @return
     */
    @GetMapping("info/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:info')")
    public Result<SysRoleDetailVo> loadSysRoleDetailInfo(@PathVariable Long roleId) {
        SysRoleDetailVo roleDetailVo = sysRoleService.querySysRoleDetailById(roleId);
        return Result.success(roleDetailVo);
    }

    /**
     * 修改角色信息
     * @param addOrUpdateParam
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result<String> modifySysRole(@RequestBody SysRoleAddOrUpdateParam addOrUpdateParam) {
        Integer count = sysRoleService.modifySysRole(addOrUpdateParam);
        return Result.handle(count>0);
    }

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result<String> removeSysRoles(@RequestBody List<Long> roleIds) {
        Integer count = sysRoleService.removeSysRolesByIds(roleIds);
        return Result.handle(count==roleIds.size());
    }

}
