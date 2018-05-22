package com.acegear.horizon.vendor.ons;

import com.acegear.horizon.domain.events.BaseEvent;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by wangsike on 2017/3/22.
 */
@Component
public class ONSPublisher {

    @Value("${horizon.ons.topic}")
    private String topic;

    @Autowired
    private Producer producer;

    @Autowired
    private ObjectMapper objectMapper;

    public <T extends BaseEvent> void sendTimingMessage(T t, long timing) {
        Message message = getMessage(t);
        if (message == null) return;
        message.setStartDeliverTime(timing);
        send(message, String.valueOf(t.getEventId()));
    }

    public <T extends BaseEvent> void sendMessage(T t) {
        Message message = getMessage(t);
        if (message == null) return;
        send(message, String.valueOf(t.getEventId()));
    }

    private void send(Message message, String shardingKey) {
        producer.send(message);
    }

    private <T extends BaseEvent> Message getMessage(T t) {
        try {
            return new Message(topic,
                    t.getClass().getName(),
                    "event:" + t.getEventId(),
                    objectMapper.writeValueAsBytes(t));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
