package com.minnow.strategy;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginStrategy {

    /**
     * 真正的登录处理方法
     * @param username
     * @return
     */
    UserDetails realLogin(String username);
}
