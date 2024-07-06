package com.minnow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minnow.domain.User;
import com.minnow.dto.SysUserQueryParam;
import com.minnow.model.Result;
import com.minnow.service.UserService;
import com.minnow.util.AuthUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("sys/user")
@RestController
public class SysUserController {
    private UserService userService;

    /**
    根据用户ID查询用户信息
     */
    @GetMapping("info")
    public Result<User> loadSysUserInfo() {
        // 获取用户ID
        Long userId = AuthUtils.getLoginUserId();
        // 根据用户ID查询用户信息
        User sysUser = userService.getById(userId);
        return Result.success(sysUser);
    }

    /**
     * 分页查询
     * @param page
     * @param queryParam
     * @return
     */
    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<Page<User>> loadSysUserPage(Page<User> page, SysUserQueryParam queryParam) {
        page = userService.page(page, new LambdaQueryWrapper<User>()
                .like(StringUtils.hasText(queryParam.getUsername()), User::getUserName, queryParam.getUsername())
                .like(queryParam.getId() != -1, User::getId, queryParam.getId())
        );
        return Result.success(page);
    }

    @ApiOperation("新增用户")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> saveSysUser(@RequestBody User user) {
        Integer i = userService.addSysUser(user);
        return Result.handle(i>0);
    }

    /**
     * 更新庄户信息
     * @param user
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> modifySysUser(@RequestBody User user) {
        Integer count = userService.modifySysUser(user);
        return Result.handle(count>0);
    }

    /**
     * 批量删除账户
     * @param userIds
     * @return
     */
    @DeleteMapping("{userIds}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> removeSysUsers(@PathVariable List<Long> userIds) {
        Integer count = userService.removeSysUsersByUserIdList(userIds);
        return Result.handle(count==userIds.size());
    }

}
