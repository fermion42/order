package com.acegear.horizon.domain.repository;

import com.acegear.horizon.domain.models.Order;
import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import com.acegear.horizon.domain.models.jpa.ProductActivity;
import com.acegear.horizon.domain.models.vo.OrderVO;
import com.acegear.horizon.domain.repository.jpa.EventOrderRepository;
import com.acegear.horizon.domain.repository.jpa.ProductActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class OrderRepository {
    @Autowired
    private EventOrderRepository eventOrderRepository;
    @Autowired
    private ProductActivityRepository productActivityRepository;


    public Optional<Order> findByOrderId(String orderId) {
        return eventOrderRepository.findByOrderId(orderId).map(Order::new);
    }

    public List<OrderVO> getOrder(Long userId, OrderState state, Date startAt, Date endAt, Pageable pageable) {
        return eventOrderRepository
                .findAll((root, cq, cb) -> {
                    List<Predicate> list = new ArrayList<>();

                    if (userId != null) {
                        list.add(cb.equal(root.get("userId"), userId));
                    }
                    if (state != null) {
                        list.add(cb.equal(root.get("state"), state));
                    }
                    if (startAt != null) {
                        list.add(cb.greaterThanOrEqualTo(root.get("createAt"), startAt));
                    }
                    if (endAt != null) {
                        list.add(cb.lessThanOrEqualTo(root.get("createAt"), endAt));
                    }
                    list.add(cb.equal(root.get("deleted"), false));
                    Predicate[] predicates = new Predicate[list.size()];

                    cq.where(cb.and(list.toArray(predicates)));
                    return cq.getRestriction();
                }, pageable)
                .getContent()
                .stream()
                .map(Order::new)
                .map(Order::getOrderVO)
                .collect(Collectors.toList());
    }

    public List<OrderVO> query(Long eventId, Long itemId, Pageable pageable) {
        if (eventId != null) {
            return eventOrderRepository.findByEventId(eventId, pageable)
                    .stream()
                    .map(Order::new)
                    .map(Order::getOrderVO)
                    .collect(Collectors.toList());
        } else {
            return eventOrderRepository.findByItemId(itemId, pageable)
                    .stream()
                    .map(Order::new)
                    .map(Order::getOrderVO)
                    .collect(Collectors.toList());
        }
    }

    public void clubEventEnd() {
        List<ProductActivity> productActivities = (List<ProductActivity>) productActivityRepository.findAll();
        for (ProductActivity productActivity : productActivities) {
            Long time = System.currentTimeMillis();
            if (time > productActivity.getEndAt()) {
                List<EventOrder> eventOrders = eventOrderRepository.findByEventId(productActivity.getActivityId());
                eventOrders.stream().filter(eventOrder -> eventOrder.getState().equals(OrderState.PAYED)).forEach(eventOrder -> {
                    eventOrder.setState(OrderState.CONSUMED);
                    eventOrderRepository.save(eventOrder);
                });
            }

        }
    }

}
