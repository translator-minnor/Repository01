package com.minnow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minnow.constant.BusinessEnum;
import com.minnow.constant.HttpConstants;
import com.minnow.filter.TokenTranslationFilter;
import com.minnow.model.Result;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private TokenTranslationFilter tokenTranslationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域
        http.cors().disable();
        //关闭跨站
        http.csrf().disable();
        //关闭session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenTranslationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())//处理到这里还未携带token的请求
                .accessDeniedHandler(accessDeniedHandler());//处理携带token但权限不足的请求

        //配置其他请求
        http.authorizeHttpRequests()
                .antMatchers()
                .permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType(HttpConstants.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            Result<Object> result = Result.fail(BusinessEnum.UN_AUTHORIZATION);
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, authException) -> {
            response.setContentType(HttpConstants.CONTENT_TYPE);
            response.setCharacterEncoding(HttpConstants.UTF_8);

            Result<Object> result = Result.fail(BusinessEnum.ACCESS_DENY_FAIL);
            String s = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.write(s);
            writer.flush();
            writer.close();
        };
    }

}
