package com.acegear.horizon.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by wangsike on 2017/3/22.
 */
@Configuration
public class ONSConfig {
    @Value("${horizon.ons.accessKey}")
    private String accessKey;
    @Value("${horizon.ons.secretKey}")
    private String secretKey;
    @Value("${horizon.ons.pid}")
    private String pid;
    @Value("${horizon.ons.cid}")
    private String cid;
    @Value("${horizon.ons.addr}")
    private String addr;

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, accessKey);
        properties.put(PropertyKeyConst.SecretKey, secretKey);
        properties.put(PropertyKeyConst.ConsumerId, cid);
        properties.put(PropertyKeyConst.ProducerId, pid);
        properties.put(PropertyKeyConst.ONSAddr, addr);

        return properties;
    }

    @Bean
    public Producer getProducer() {
        Producer producer = ONSFactory.createProducer(getProperties());
        producer.start();
        return producer;
    }

    @Bean
    public Consumer getConsumer() {
        Consumer consumer = ONSFactory.createConsumer(getProperties());
        consumer.start();
        return consumer;
    }
}
