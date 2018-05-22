package com.acegear.horizon.domain.events;

/**
 * Created by wangsike on 2017/5/9.
 */
public class OrderConsumeEvent extends BaseEvent {
    private String orderId;

    private Long productId;

    private Long productItemId;

    public OrderConsumeEvent() {
    }

    public OrderConsumeEvent(String orderId, Long productId, Long productItemId) {
        super();
        this.orderId = orderId;
        this.productId = productId;
        this.productItemId = productItemId;
        genEventId();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    @Override
    public String routingKey() {
        return null;
    }
}
