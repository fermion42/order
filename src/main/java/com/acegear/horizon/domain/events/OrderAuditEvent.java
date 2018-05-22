package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/7/6.
 */
public class OrderAuditEvent extends BaseEvent {
    private String orderId;
    private Boolean result;
    private String reason;

    public OrderAuditEvent() {
    }

    public OrderAuditEvent(String orderId, Boolean result, String reason) {
        this.orderId = orderId;
        this.result = result;
        this.reason = reason;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.order.audit";
    }
}
