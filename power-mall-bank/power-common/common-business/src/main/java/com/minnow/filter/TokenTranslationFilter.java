package com.minnow.filter;

import com.alibaba.fastjson.JSON;
import com.minnow.constant.AuthConstants;
import com.minnow.model.SecurityUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class TokenTranslationFilter extends OncePerRequestFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(AuthConstants.AUTHORIZATION);
        if (StringUtils.hasText(authorization)) {
            String token = authorization.replaceFirst(AuthConstants.BEARER, "");
            if (StringUtils.hasText(token)) {
                //获取剩余时间redis
                Long expire = stringRedisTemplate.getExpire(AuthConstants.LOGIN_TOKEN_PREFIX + token, TimeUnit.SECONDS);
                if (expire < AuthConstants.TOKEN_EXPIRE_THRESHOLD_TIME) {
                    // token续约
                    stringRedisTemplate.expire(AuthConstants.LOGIN_TOKEN_PREFIX + token, Duration.ofSeconds(AuthConstants.TOKEN_TIME));
                }
                //将token转化为用户信息
                //从reids中获取用户信息
                String userInfo = stringRedisTemplate.opsForValue().get(AuthConstants.LOGIN_TOKEN_PREFIX + token);
                SecurityUser securityUser = JSON.parseObject(userInfo, SecurityUser.class);
                Set<SimpleGrantedAuthority> grantedAuthorities = securityUser.getPerms()
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                // 创建security框架认识的用户对象
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser,null,grantedAuthorities);
                // 将security框架认识的用户对象存放到security上下文中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
