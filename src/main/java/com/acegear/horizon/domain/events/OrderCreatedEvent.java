package com.acegear.horizon.domain.events;


import com.acegear.horizon.domain.models.jpa.EventOrderItem;
import com.acegear.horizon.domain.models.jpa.EventOrderSignUpExtra;

import java.util.List;

/**
 * Created by zxx on 2017/1/5.
 */
public class OrderCreatedEvent extends BaseEvent {
    private String orderId;

    private Long userId;

    private Long eventId;

    private String eventName;

    private List<EventOrderItem> items;

    private List<EventOrderSignUpExtra> signItems;
    private Long expireAt;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, Long userId, Long eventId, String eventName,  List<EventOrderItem> items, List<EventOrderSignUpExtra> signItems, Long expireAt) {
        super();
        this.orderId = orderId;
        this.userId = userId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.items = items;
        this.signItems = signItems;
        this.expireAt = expireAt;
        genEventId();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    public List<EventOrderItem> getItems() {
        return items;
    }

    public void setItems(List<EventOrderItem> items) {
        this.items = items;
    }

    public List<EventOrderSignUpExtra> getSignItems() {
        return signItems;
    }

    public void setSignItems(List<EventOrderSignUpExtra> signItems) {
        this.signItems = signItems;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.order.created";
    }
}
