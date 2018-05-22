package com.acegear.horizon.domain.service;

import com.acegear.horizon.domain.models.Product;
import com.acegear.horizon.domain.models.constraint.ProductActivityState;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import com.acegear.horizon.domain.models.jpa.EventOrderItem;
import com.acegear.horizon.domain.models.jpa.ProductActivity;
import com.acegear.horizon.domain.models.jpa.ProductItem;
import com.acegear.horizon.domain.repository.ProductRepository;
import com.acegear.horizon.domain.repository.jpa.EventOrderRepository;
import com.acegear.horizon.domain.repository.jpa.ProductItemRepository;
import javaslang.Tuple;
import javaslang.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wangsike on 2017/1/16.
 */
@Service
public class EventService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private EventOrderRepository eventOrderRepository;

    public Boolean checkEventOrder(EventOrder eventOrder) {
        Boolean canDeduct;
        Optional<Product> productOptional = productRepository.findByRelateId(eventOrder.getEventId());
        if (productOptional.isPresent()) {
            Optional<ProductActivity> productActivityOptional = productOptional.get().getProductActivity();
            if (productActivityOptional.isPresent()) {
                if (productActivityOptional.get().getState() == ProductActivityState.END) {
                    return false;
                }
            } else {
                return false;
            }
            List<Tuple2<ProductItem, Integer>> tuple2s = new ArrayList<>();
            for (EventOrderItem orderItem : eventOrder.getItems()) {
                ProductItem productItem = productItemRepository.findByItemId(orderItem.getItemId()).get();
                canDeduct = productItem.canDeduct(orderItem.getCount());
                tuple2s.add(Tuple.of(productItem, orderItem.getCount()));
                if (canDeduct) {
                    tuple2s.forEach(tuple2 ->
                            tuple2._1.deduct(tuple2._2));
                    try {
                        productItemRepository.save(
                                tuple2s
                                        .stream()
                                        .map(Tuple2::_1)
                                        .collect(Collectors.toList()));
                        return true;
                    } catch (Throwable t) {
                        t.printStackTrace();
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Boolean cancelOrder(String orderId) {
        Optional<EventOrder> eventOrderOptional = eventOrderRepository.findByOrderId(orderId);
        List<ProductItem> productItems = new ArrayList<>();
        if (eventOrderOptional.isPresent()) {
            EventOrder eventOrder = eventOrderOptional.get();
            for (EventOrderItem orderItem : eventOrder.getItems()) {
                ProductItem productItem = productItemRepository.findByItemId(orderItem.getItemId())
                        .get();
                productItem.undo(orderItem.getCount());
                productItems.add(productItem);
            }
            try {
                productItemRepository.save(productItems);
                return true;
            } catch (Throwable t) {
                t.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean isAudit(Long eventId) {
        ProductActivity productActivity = getEventVO(eventId);
        if (productActivity.isPayAudit()) {
            return true;
        }
        return false;
    }


    public ProductActivity getEventVO(Long eventId) {
        return productRepository.findByActivityId(eventId).get();
    }

}