package com.minnow.impl;

import com.minnow.strategy.LoginStrategy;
import com.minnow.strategy.impl.UserLoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LoginStrategy loginStrategy = new UserLoginStrategy();

        return loginStrategy.realLogin(username);
    }

}
