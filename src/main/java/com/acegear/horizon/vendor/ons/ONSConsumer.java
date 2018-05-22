package com.acegear.horizon.vendor.ons;

import com.acegear.horizon.domain.events.BaseEvent;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

/**
 * Created by wangsike on 2017/3/22.
 */
@Component
public class ONSConsumer {

    @Value("${horizon.ons.topic}")
    private String topic;

    @Autowired
    private com.aliyun.openservices.ons.api.Consumer consumer;

    @Autowired
    private ObjectMapper objectMapper;

    private ConcurrentHashMap<String, Consumer> consumeTable;

    public ONSConsumer() {
        consumeTable = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.consumer.subscribe(topic, "*", (Message message, ConsumeContext context) -> {
            try {
                String className = message.getTag();
                if (consumeTable.containsKey(className)) {
                    Class tClass = Class.forName(className);
                    Object t = objectMapper.readValue(message.getBody(), tClass);
                    consumeTable.get(className).accept(t);
                }
                return Action.CommitMessage;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return Action.ReconsumeLater;
            }
        });
    }

    public <T extends BaseEvent> void consume(Class<T> tClass, Consumer<T> consumer) {
        consumeTable.put(tClass.getName(), consumer);
    }
}
