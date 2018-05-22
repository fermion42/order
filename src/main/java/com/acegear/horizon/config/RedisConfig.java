package com.acegear.horizon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by wangsike on 2017/1/12.
 */
@Configuration
public class RedisConfig {
    @Value("${horizon.cache.redis.url}")
    private String redisUrl;

    @Value("${horizon.cache.redis.auth}")
    private String redisAuth;

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        String[] url = redisUrl.split(":");
        factory.setHostName(url[0]);
        if (url.length != 1) {
            factory.setPort(Integer.parseInt(url[1]));
        }
        if (!redisAuth.isEmpty()) {
            factory.setPassword(redisAuth);
        }
        return factory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(getJedisConnectionFactory());
        return template;
    }
}
