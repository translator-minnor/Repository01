package com.minnow.strategy.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minnow.domain.LoginSysUser;
import com.minnow.mapper.LoginSysUserMapper;
import com.minnow.model.SecurityUser;
import com.minnow.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;
import java.util.Set;

public class UserLoginStrategy implements LoginStrategy {

    @Resource
    private LoginSysUserMapper userMapper;

    @Override
    public UserDetails realLogin(String username) {

        LoginSysUser loginSysUser = userMapper.selectOne(new LambdaQueryWrapper<LoginSysUser>().eq(LoginSysUser::getUserName, username));

        if (ObjectUtil.isNotNull(loginSysUser)) {
            Set<String> perms = userMapper.selectPermsByUserId(loginSysUser.getRoleId());

            SecurityUser securityUser = SecurityUser.builder()
                    .userName(username)
                    .id(loginSysUser.getId())
                    .password(loginSysUser.getPassword())
                    .roleId(loginSysUser.getRoleId())
                    .build();

            if (ObjectUtil.isNotNull(perms)) {
                securityUser.setPerms(perms);
            }
            return securityUser;
        }

        return null;
    }
}
