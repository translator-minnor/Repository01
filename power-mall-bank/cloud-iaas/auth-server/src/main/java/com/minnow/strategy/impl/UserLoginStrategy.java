package com.minnow.strategy.impl;

import com.minnow.strategy.LoginStrategy;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLoginStrategy implements LoginStrategy {
    @Override
    public UserDetails realLogin(String username) {
        return null;
    }
}
