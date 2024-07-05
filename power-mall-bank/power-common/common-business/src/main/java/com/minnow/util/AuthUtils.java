package com.minnow.util;

import com.minnow.model.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

public class AuthUtils {

    /**
     * 获取Security容器中的认证用户对象
     * @return
     */
    public static SecurityUser getLoginUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取登录用户ID
     * @return
     */
    public static Long getLoginUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取登录用户的权限集合
     * @return
     */
    public static Set<String> getPerms() {
        return getLoginUser().getPerms();
    }
}
