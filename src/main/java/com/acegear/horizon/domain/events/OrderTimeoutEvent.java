package com.acegear.horizon.domain.events;

/**
 * Created by wangsike on 2017/1/16.
 */
public class OrderTimeoutEvent extends BaseEvent {
    private String orderId;

    public OrderTimeoutEvent() {
    }

    public OrderTimeoutEvent(String orderId) {
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
        return "horizon.domain.order.timeout";
    }
}
