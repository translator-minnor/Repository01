package com.minnow.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minnow.config.WhiteUrlsConfig;
import com.minnow.constant.AuthConstants;
import com.minnow.constant.BusinessEnum;
import com.minnow.constant.HttpConstants;
import com.minnow.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private WhiteUrlsConfig whiteUrlsConfig;
    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //获取请求地址
        String url = request.getPath().toString();
        //白名单地址放行
        if (whiteUrlsConfig.getAllowUrls().contains(url)) {
            return chain.filter(exchange);
        }
        //非白名单
        //获取令牌
        String auth = request.getHeaders().getFirst(AuthConstants.AUTHORIZATION);
        //检验令牌是否为空
        if (StringUtils.hasText(auth)) {
            //检验令牌内容是否有效
            String token = auth.replaceFirst(AuthConstants.BEARER, "");
            if (StringUtils.hasText(token) && redisTemplate.hasKey(AuthConstants.LOGIN_TOKEN_PREFIX + token)) {
                return chain.filter(exchange);
            }
        }

        //非白名单，令牌无效
        log.error("拦截非法请求，拦截时间:{}，请求api接口:{}", new Date(), url);
        //设置响应对象
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON);
        //创建响应结果
        Result result = Result.fail(BusinessEnum.UN_AUTHORIZATION);
        byte[] s = new byte[0];

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            s = objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        DataBuffer wrap = response.bufferFactory().wrap(s);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}
