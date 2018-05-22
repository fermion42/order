package com.acegear.horizon.domain.models.vo;

import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.EventOrderItem;
import com.acegear.horizon.domain.models.jpa.EventOrderSignUpExtra;

import java.util.List;

/**
 * Created by wangsike on 2017/1/12.
 */
public class OrderVO {
    private String orderId;

    private Long totalPrice;

    private Long eventId;

    private String eventName;

    private String eventCover;

    private OrderState state;

    private List<EventOrderItem> orderItems;

    private List<EventOrderSignUpExtra> signItems;

    private String clubName;

    private String clubLogo;

    private Long clubId;

    private Long createAt;

    private Long expireAt;

    private String reason;

    public OrderVO() {
    }

    public OrderVO(String orderId, Long totalPrice, Long eventId, String eventName, String eventCover,
                   OrderState state, List<EventOrderItem> orderItems, String clubName, String clubLogo, Long clubId, Long createAt, Long expireAt, List<EventOrderSignUpExtra> signItems, String reason) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventCover = eventCover;
        this.state = state;
        this.orderItems = orderItems;
        this.clubName = clubName;
        this.clubLogo = clubLogo;
        this.clubId = clubId;
        this.createAt = createAt;
        this.expireAt = expireAt;
        this.signItems = signItems;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCover() {
        return eventCover;
    }

    public void setEventCover(String eventCover) {
        this.eventCover = eventCover;
    }


    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public List<EventOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<EventOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    public List<EventOrderSignUpExtra> getSignItems() {
        return signItems;
    }

    public void setSignItems(List<EventOrderSignUpExtra> signItems) {
        this.signItems = signItems;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
