package com.minnow.config;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.minnow.constant.AuthConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取当前请求的上下文
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取当前请求对象
        if (ObjectUtils.isNotNull(requestAttributes)) {
            HttpServletRequest request = requestAttributes.getRequest();
            // 将当前请求对象头里的token传递到下一个请求对象的请求头中
            if (ObjectUtils.isNotNull(request)) {
                requestTemplate.header(AuthConstants.AUTHORIZATION,request.getHeader(AuthConstants.AUTHORIZATION));
            }
        }
        //apiFox测试用
        requestTemplate.header(AuthConstants.AUTHORIZATION,"e63658f1-b5fc-4195-afab-b9d554af3658");
    }
}
