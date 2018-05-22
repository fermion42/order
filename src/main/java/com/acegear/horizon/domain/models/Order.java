package com.acegear.horizon.domain.models;

import com.acegear.horizon.domain.events.*;
import com.acegear.horizon.domain.models.jpa.*;
import com.acegear.horizon.domain.models.vo.OrderVO;
import com.acegear.horizon.domain.repository.jpa.ClubBaseRepository;
import com.acegear.horizon.domain.repository.jpa.EventOrderRepository;
import com.acegear.horizon.domain.service.EventService;
import com.acegear.horizon.domain.service.OrderService;
import com.acegear.horizon.utils.OrderUtils;
import com.acegear.horizon.utils.SpringUtils;
import com.acegear.horizon.vendor.ons.ONSPublisher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by wangsike on 2017/1/11.
 */
public class Order {
    private EventOrder eventOrder;

    private EventOrderRepository eventOrderRepository;

    private OrderService orderService;

    private EventService eventService;

    private ONSPublisher publisher;

    public static volatile Long expireTime;

    private ClubBaseRepository clubBaseRepository;

    private void init() {
        eventOrderRepository = SpringUtils.getBean(EventOrderRepository.class);
        orderService = SpringUtils.getBean(OrderService.class);
        eventService = SpringUtils.getBean(EventService.class);
        publisher = SpringUtils.getBean(ONSPublisher.class);
        clubBaseRepository = SpringUtils.getBean(ClubBaseRepository.class);
    }

    public Order() {
        init();
    }

    public Order(EventOrder eventOrder) {
        init();
        this.eventOrder = eventOrder;
    }

    private Order(Long userId,
                  Long eventId,
                  String eventName,
                  List<EventOrderItem> items,
                  List<EventOrderSignUpExtra> signItem) {
        init();
        String orderId = OrderUtils.newOrderId();
        eventOrder = new EventOrder(
                orderId,
                userId,
                eventId,
                eventName,
                items,
                System.currentTimeMillis() + expireTime,
                signItem);
    }

    public static Optional<Order> createOrder(Long userId,
                                              Long eventId,
                                              String eventName,
                                              List<EventOrderItem> items,
                                              List<EventOrderSignUpExtra> signItem) {
        Order order = new Order(userId, eventId, eventName, items, signItem);
        if (order.checkOrder()) {
            order.save();
            order.publishOrderCreated();
            order.publishOrderDelayTimeOut();
            return Optional.of(order);
        } else {
            return Optional.empty();
        }
    }


    public Optional<OrderCharge> createCharge(PayChannel payChannel, Long userId) {
        if (eventOrder.isPayed() || eventOrder.isTimeout() || eventOrder.isCanceled()) {
            return Optional.empty();
        } else {
            Map<String, Object> params = orderService.getChargeParams(this, payChannel, userId);
            return OrderCharge.create(params);
        }
    }

    public void clientPayed() {
        eventOrder.unsettled();
        save();
    }

    public boolean payed() {
        if (eventOrder.isTimeout() || eventOrder.isCanceled()) {
            return false;
        } else if (eventOrder.isPayed()) {
            return true;
        } else if (eventOrder.getTotalPrice() != 0L) {
            clientPayed();
            return true;
        } else {
            serverPayed();
            return true;
        }
    }

    public void serverPayed() {
        if (eventService.isAudit(eventOrder.getEventId())) {
            eventOrder.audit();
        } else {
            eventOrder.payed();
        }
        publishOrderPayed();
        save();
    }

    public Boolean userConsume() {
        Boolean consume = eventOrder.consume();
        save();
        return consume;
    }

    public Long getEventId() {
        return eventOrder.getEventId();
    }

    public String getOrderId() {
        return eventOrder.getOrderId();
    }

    private Boolean checkOrder() {
        return eventService.checkEventOrder(eventOrder);
    }

    private Boolean cancelOrder() {
        return eventService.cancelOrder(getOrderId());
    }

    public Order save() {
        eventOrder = eventOrderRepository.save(eventOrder);
        return this;
    }

    private void publishOrderCreated() {
        publisher.sendMessage(new OrderCreatedEvent(getOrderId(), eventOrder.getUserId(), eventOrder.getEventId(), eventOrder.getEventName(), eventOrder.getItems(), eventOrder.getSignItems(), eventOrder.getExpireAt()));
    }


    private void publishOrderPayed() {
        publisher.sendMessage(new OrderPayedEvent(getOrderId()));
    }

    private void publishOrderDelayTimeOut() {
        publisher.sendTimingMessage(new DelayTimeOutEvent(getOrderId()), System.currentTimeMillis() + expireTime);
    }

    private void publishOrderTimeout() {
        publisher.sendMessage(new OrderTimeoutEvent(getOrderId()));
    }

    private void publishOrderCancel() {
        publisher.sendMessage(new OrderCancelEvent(getOrderId()));
    }

    private void publishOrderDelete() {
        publisher.sendMessage(new OrderDeleteEvent(getOrderId()));
    }


    public EventOrder getEventOrder() {
        return eventOrder;
    }

    public void setEventOrder(EventOrder eventOrder) {
        this.eventOrder = eventOrder;
    }

    public OrderVO getOrderVO() {
        ProductActivity eventVO = eventService.getEventVO(getEventId());
        ClubBase clubBase = clubBaseRepository.findByClubId(eventVO.getOwnerId()).get();
        return new OrderVO(
                getOrderId(),
                eventOrder.getTotalPrice(),
                getEventId(),
                eventVO.getName(),
                eventVO.getCover(),
                eventOrder.getState(),
                eventOrder.getItems(),
                clubBase.getClubName(),
                clubBase.getLogo(),
                eventVO.getOwnerId(),
                eventOrder.getCreateAt(),
                eventOrder.getExpireAt(),
                eventOrder.getSignItems(),
                eventOrder.getReason()
        );
    }

    public void timeout() {
        if (!eventOrder.isPayed() && !eventOrder.isCanceled() && !eventOrder.isTimeout() && !eventOrder.isAudit() && !eventOrder.isAuditFail()) {
            eventOrder.timeout();
            cancelOrder();
            publishOrderTimeout();
            save();
        }
    }

    public Boolean canceled() {
        if ((!eventOrder.isPayed() && !eventOrder.isCanceled() && !eventOrder.isTimeout())) {
            eventOrder.canceled();
            cancelOrder();
            publishOrderCancel();
            save();
            return true;
        } else {
            return false;
        }
    }

    public Boolean deleteOrder() {
        if ((eventOrder.isCanceled() || eventOrder.isTimeout() || eventOrder.isAuditFail() || eventOrder.isConsumed())) {
            eventOrder.setDeleted(true);
            save();
            publishOrderDelete();
            return true;
        }
        return false;
    }

    public Boolean checkUserId(Long userId) {
        return eventOrder.getUserId().equals(userId);
    }
}
