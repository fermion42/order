package com.acegear.horizon.domain.events;

/**
 * Created by zxx on 2017/1/5.
 */
public class OrderPayedEvent extends BaseEvent {
    private String orderId;

    public OrderPayedEvent() {
    }

    public OrderPayedEvent(String orderId) {
        super();
        this.orderId = orderId;
        genEventId();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.order.payed";
    }
}
