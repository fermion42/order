package com.acegear.horizon.controller;

import com.acegear.horizon.controller.vo.ResultVO;
import com.acegear.horizon.domain.models.Order;
import com.acegear.horizon.domain.models.OrderCharge;
import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.PayChannel;
import com.acegear.horizon.domain.models.jpa.EventOrderItem;
import com.acegear.horizon.domain.models.jpa.EventOrderSignUpExtra;
import com.acegear.horizon.domain.repository.OrderChargeRepository;
import com.acegear.horizon.domain.repository.OrderRepository;
import com.acegear.horizon.domain.service.OrderService;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderChargeRepository orderChargeRepository;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity createOrder(@RequestBody CreateOrderBody createOrderBody, @RequestHeader(value = "X-Consumer-Username", required = false) Long userId) {
        if (userId == 0) {
            return ResponseEntity.badRequest().body(ResultVO.ORDER_ITEMS_VERIFY_FAILED);
        }
        return ResponseEntity.ok(Order.createOrder(userId,
                createOrderBody.getEventId(),
                createOrderBody.getEventName(),
                createOrderBody.getItems(),
                createOrderBody.getSignItems())
                .map(Order::getOrderVO)
                .map(ResultVO::new)
                .orElse(ResultVO.ORDER_ITEMS_VERIFY_FAILED));
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResultVO getOrder(@PathVariable String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(Order::getOrderVO)
                .map(ResultVO::new)
                .orElse(ResultVO.ORDER_NOT_FOUND);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResultVO deleteOrder(@PathVariable String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(Order::deleteOrder)
                .map(order -> {
                    if (order) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("success", true);
                        return new ResultVO<>(result);
                    } else {
                        return ResultVO.ORDER_CANT_DELETE;
                    }
                })
                .orElse(ResultVO.ORDER_NOT_FOUND);
    }

    @RequestMapping(value = "/{orderId}/charge", method = RequestMethod.POST)
    public HttpEntity createCharge(@PathVariable String orderId, PayChannel payChannel,
                                   @RequestHeader(value = "X-Consumer-Username", required = false) Long userId) {
        if (userId == 0) {
            return ResponseEntity.badRequest().body(ResultVO.API_ERROR);
        }
        return ResponseEntity.ok(orderRepository
                .findByOrderId(orderId)
                .flatMap(order -> order.createCharge(payChannel, userId))
                .map(orderCharge -> orderCharge.getPingxxCharge().getChargeJson())
                .map(ResultVO::new)
                .orElse(ResultVO.API_ERROR));
    }

    @RequestMapping(value = "/{orderId}/payed", method = RequestMethod.POST)
    public ResultVO unsettledOrder(@PathVariable String orderId) {
        return orderRepository
                .findByOrderId(orderId)
                .filter(Order::payed)
                .map(order -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    return result;
                })
                .map(ResultVO::new)
                .orElse(ResultVO.ORDER_PAYED_FAILED);
    }

    @RequestMapping(value = "/{orderId}/cancel", method = RequestMethod.POST)
    public ResultVO cancelOrder(@PathVariable String orderId) {
        return orderRepository
                .findByOrderId(orderId)
                .map(Order::canceled)
                .map(order -> {
                    if (order) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("success", true);
                        return new ResultVO<>(result);
                    } else {
                        return ResultVO.ORDER_CANT_CANCEL;
                    }
                })
                .orElse(ResultVO.ORDER_NOT_FOUND);
    }


    @RequestMapping(value = "/{orderId}/qr", method = RequestMethod.GET)
    public ResultVO getQrCode(@PathVariable String orderId,
                              @RequestHeader(value = "X-Consumer-Username", required = false, defaultValue = "0") Long userId) {
        return orderRepository.findByOrderId(orderId)
                .filter(order -> order.checkUserId(userId))
                .map(order -> {
                    String url = orderService.getQrUrl(order);
                    Map<String, String> result = new HashMap<>();
                    result.put("url", url);
                    return new ResultVO<>(result);
                })
                .orElse(ResultVO.ORDER_NOT_FOUND);
    }

    @RequestMapping(value = "/{orderId}/consume", method = RequestMethod.POST)
    public ResultVO consumeOrder(@PathVariable String orderId,
                                 @RequestHeader(value = "X-Consumer-Username", required = false, defaultValue = "0") Long userId,
                                 String signature) {
        return orderRepository.findByOrderId(orderId)
                .filter(order -> orderService.verify(order, userId, signature))
                .map(Order::userConsume)
                .map(success -> {
                    Map<String, Boolean> result = new HashMap<>();
                    result.put("success", success);
                    return new ResultVO<>(result);
                })
                .orElse(ResultVO.ORDER_NOT_FOUND);
    }

    @RequestMapping(value = "/callback")
    public ResponseEntity callBack(HttpServletRequest request) {
        try {
            String jsonBody = IOUtils.toString(request.getInputStream(), "utf8");
            Event event = Webhooks.eventParse(jsonBody);
            System.out.println(jsonBody);
            if (event.getType().equals("charge.succeeded")) {
                Charge charge = (Charge) event.getData().getObject();
                OrderCharge orderCharge = orderChargeRepository
                        .findByChargeId(charge.getId())
                        .get();
                orderCharge.updateCharge(charge);
                Order order = orderRepository
                        .findByOrderId(charge.getOrderNo())
                        .get();
                if (charge.getPaid()) {
                    order.serverPayed();
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultVO getOrder(@RequestHeader("X-Consumer-Username") Long userId, OrderState state, Date startAt, Date endAt, Integer page, Integer count) {
        Pageable pageable = new PageRequest(page, count, Sort.Direction.DESC, "createAt");
        return new ResultVO<>(orderRepository.getOrder(userId, state, startAt, endAt, pageable));
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public ResultVO queryOrder(Long eventId, Long itemId, int page, int count) {
        Pageable pageable = new PageRequest(page, count);
        return new ResultVO<>(orderRepository.query(eventId, itemId, pageable));
    }
}

class CreateOrderBody {
    private Long eventId;
    private String eventName;
    private List<EventOrderItem> items;
    private List<EventOrderSignUpExtra> signItems;

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

    public List<EventOrderSignUpExtra> getSignItems() {
        return signItems;
    }

    public void setSignItems(List<EventOrderSignUpExtra> signItems) {
        this.signItems = signItems;
    }
}