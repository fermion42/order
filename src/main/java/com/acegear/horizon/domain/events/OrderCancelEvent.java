package com.acegear.horizon.domain.events;



/**
 * Created by wangsike on 2017/5/9.
 */
public class OrderCancelEvent extends BaseEvent {

    private String orderId;

    public OrderCancelEvent() {
    }

    public OrderCancelEvent(String orderId) {
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
        return null;
    }
}
