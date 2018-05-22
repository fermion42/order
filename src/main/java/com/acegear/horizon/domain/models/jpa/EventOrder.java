package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.converter.SignUpExtraConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangsike on 2017/1/11.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EventOrder {

    @Column(unique = true)
    private String orderId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long eventId;

    @Column
    private String eventName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = SignUpExtraConverter.class)
    private List<EventOrderSignUpExtra> signItems;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EventOrderItem> items;

    @Column
    @Enumerated
    private OrderState state;

    @Column
    private boolean deleted;

    @Column
    private String reason;

    @Column
    private Long expireAt;

    @CreatedDate
    @Column
    private Long createAt;

    @LastModifiedDate
    @Column
    private Long updateAt;

    public EventOrder() {

    }

    public EventOrder(String orderId,
                      Long userId,
                      Long eventId,
                      String eventName,
                      List<EventOrderItem> items,
                      Long expireAt,
                      List<EventOrderSignUpExtra> signItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.items = items;
        this.expireAt = expireAt;
        state = OrderState.CREATED;
        this.signItems = signItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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


    public List<EventOrderItem> getItems() {
        return items;
    }

    public void setItems(List<EventOrderItem> items) {
        this.items = items;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void unsettled() {
        state = OrderState.UNSETTLED;
    }

    public void payed() {
        state = OrderState.PAYED;
    }

    public void audit() {
        state = OrderState.AUDIT;
    }

    public void auditFail() {
        state = OrderState.AUDITFAIL;
    }

    public Boolean consume() {
        if (state == OrderState.PAYED) {
            state = OrderState.CONSUMED;
            return true;
        } else {
            return false;
        }
    }

    public Boolean canceled() {
        if (state != OrderState.CANCELED) {
            state = OrderState.CANCELED;
            return true;
        } else {
            return false;
        }
    }

    public void timeout() {
        state = OrderState.TIMEOUT;
    }

    public Long getTotalPrice() {
        return items
                .stream()
                .map(EventOrderItem::getItemPrice)
                .reduce((i, j) -> i + j)
                .get();
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public Boolean isPayed() {
        return state == OrderState.UNSETTLED || state == OrderState.PAYED || state == OrderState.CONSUMED;
    }

    public Boolean isCanceled() {
        return state == OrderState.CANCELED;
    }

    public Boolean isTimeout() {
        return state == OrderState.TIMEOUT;
    }

    public Boolean isConsumed() {
        return state == OrderState.CONSUMED;
    }

    public Boolean isAudit() {
        return state == OrderState.AUDIT;
    }

    public Boolean isAuditFail() {
        return state == OrderState.AUDITFAIL;
    }

    public List<EventOrderSignUpExtra> getSignItems() {
        if(signItems==null){
            return Collections.emptyList();
        }
        return signItems;
    }

    public void setSignItems(List<EventOrderSignUpExtra> signItems) {
        this.signItems = signItems;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



}
