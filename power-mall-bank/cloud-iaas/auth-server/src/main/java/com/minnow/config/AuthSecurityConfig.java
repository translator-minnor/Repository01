package com.minnow.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minnow.constant.AuthConstants;
import com.minnow.constant.BusinessEnum;
import com.minnow.constant.HttpConstants;
import com.minnow.impl.UserDetailsServiceImpl;
import com.minnow.model.LoginResult;
import com.minnow.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.UUID;

@Configuration
@Slf4j
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭跨站请求伪造的拦截
        http.csrf().disable();
        // 关闭跨域请求
        http.cors().disable();
        // 关闭session策略，不创建session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //登录信息
        http.formLogin()
                .loginProcessingUrl(AuthConstants.LOGIN_URL)//登录请求路径
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
        //登出信息
        http.logout()
                .logoutUrl(AuthConstants.LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler());
        // 配置其它请求的规则：
        // 所有接口都必须认证通过后才可以访问，也就是说必须在securityContext中有认证对象才可以访问
        http.authorizeHttpRequests()
                .anyRequest().authenticated();  // 其它任何请求都必须认证
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            //设置响应头信息
            response.setContentType(HttpConstants.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstants.UTF_8);
            //使用uuid作为token
            String token = UUID.randomUUID().toString();
            //从sec中获取用户对象
            String userJsonString = JSONObject.toJSONString(authentication.getPrincipal());
            //往redis里面塞令牌信息
            stringRedisTemplate.opsForValue().set(AuthConstants.LOGIN_TOKEN_PREFIX + token, userJsonString, Duration.ofSeconds(AuthConstants.TOKEN_TIME));
            LoginResult loginResult = new LoginResult(token, AuthConstants.TOKEN_TIME);

            Result<LoginResult> success = Result.success(loginResult);

            String s = objectMapper.writeValueAsString(success);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setContentType(HttpConstants.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            Result<Object> result = new Result<>();
            result.setData(BusinessEnum.OPERATION_FAIL.getCode());
            if (exception instanceof BadCredentialsException) {
                result.setMsg("用户或密码有误");
            } else if (exception instanceof UsernameNotFoundException) {
                result.setMsg("用户名不存在");
            } else if (exception instanceof AccountExpiredException) {
                result.setMsg("帐号异常，请联系管理员");
            } else if (exception instanceof InternalAuthenticationServiceException) {
                result.setMsg(exception.getMessage());
            } else {
                result.setMsg(BusinessEnum.OPERATION_FAIL.getDesc());
            }

            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType(HttpConstants.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            String header = request.getHeader(AuthConstants.AUTHORIZATION);
            String token = header.replaceFirst(AuthConstants.BEARER, "");

            stringRedisTemplate.delete(AuthConstants.LOGIN_TOKEN_PREFIX + token);

            Result<String> result = Result.success(null);
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
