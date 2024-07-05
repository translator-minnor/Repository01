package com.minnow.controller;

import com.minnow.domain.User;
import com.minnow.model.Result;
import com.minnow.service.UserService;
import com.minnow.util.AuthUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("sys/user")
@RestController
public class SysUserController {
    private UserService userService;

    @ApiOperation("根据用户ID查询用户信息")
    @GetMapping("info")
    public Result<User> loadSysUserInfo() {
        // 获取用户ID
        Long userId = AuthUtils.getLoginUserId();
        // 根据用户ID查询用户信息
        User sysUser = userService.getById(userId);
        return Result.success(sysUser);
    }
}
