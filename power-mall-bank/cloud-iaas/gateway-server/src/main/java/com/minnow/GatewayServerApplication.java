package com.minnow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient  // 开启nacos注册中心客户端（即：向注册中心注册自己的信息）
public class GatewayServerApplication implements ApplicationRunner {
    @Resource
    private RedisTemplate redisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }
}
