package com.acegear.horizon.domain.events;

/**
 * Created by caiyanying on 17/7/13.
 */
public class OrderDeleteEvent extends BaseEvent{
    private String orderId;

    public OrderDeleteEvent() {
    }

    public OrderDeleteEvent(String orderId) {
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
        return "horizon.domain.order.delete";
    }


}
