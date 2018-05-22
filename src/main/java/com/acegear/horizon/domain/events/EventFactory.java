package com.acegear.horizon.domain.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by wangsike on 2016/12/12.
 */
@Service
public class EventFactory {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${horizon.domain.event.idKey}")
    private String eventIdKey;

    @Value("${horizon.domain.product.idKey}")
    private String productIdKey;

    public Long nextEventId() {
        return redisTemplate.opsForValue().increment(eventIdKey,1);
    }

    public Long nextProductId() {
        return redisTemplate.opsForValue().increment(productIdKey,1);
    }

}
