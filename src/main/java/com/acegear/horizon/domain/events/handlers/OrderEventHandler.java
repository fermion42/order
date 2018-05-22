package com.acegear.horizon.domain.events.handlers;

import com.acegear.horizon.domain.events.DelayTimeOutEvent;
import com.acegear.horizon.domain.events.OrderAuditEvent;
import com.acegear.horizon.domain.events.OrderCreatedEvent;
import com.acegear.horizon.domain.events.OrderPayedEvent;
import com.acegear.horizon.domain.models.Order;
import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import com.acegear.horizon.domain.repository.OrderRepository;
import com.acegear.horizon.domain.service.EventService;
import com.acegear.horizon.vendor.ons.ONSConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangsike on 2017/1/16.
 */
@Component
public class OrderEventHandler {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private ONSConsumer consumer;

    @PostConstruct
    public void timeONS() {
        consumer.consume(DelayTimeOutEvent.class, this::timeout);
    }

    @PostConstruct
    public void auditONS() {
        consumer.consume(OrderAuditEvent.class, this::audit);
    }

    public void timeout(DelayTimeOutEvent orderTimeoutEvent) {
        orderRepository
                .findByOrderId(orderTimeoutEvent.getOrderId())
                .ifPresent(Order::timeout);
    }

    public void created(OrderCreatedEvent orderCreatedEvent) {

    }

    public void payed(OrderPayedEvent orderPayedEvent) {

    }

    public void audit(OrderAuditEvent orderAuditEvent) {
        orderRepository.findByOrderId(orderAuditEvent.getOrderId()).ifPresent(order -> {
                    EventOrder orderEvent = order.getEventOrder();
                    if (orderAuditEvent.getResult()) {
                        orderEvent.setState(OrderState.PAYED);
                        order.save();
                    } else {
                        orderEvent.setReason(orderAuditEvent.getReason());
                        orderEvent.setState(OrderState.AUDITFAIL);
                        order.save();
                        eventService.cancelOrder(orderAuditEvent.getOrderId());
                    }
                }
        );
    }
}
