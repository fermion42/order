package com.acegear.horizon.domain.repository;

import com.acegear.horizon.domain.models.OrderCharge;
import com.acegear.horizon.domain.repository.jpa.PingxxChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;


@Component
public class OrderChargeRepository {
    @Autowired
    private PingxxChargeRepository pingxxChargeRepository;

    public Optional<OrderCharge> findByChargeId(String chargeId) {
        return pingxxChargeRepository.findByChargeId(chargeId).map(OrderCharge::new);
    }

    public Stream<OrderCharge> findByOrderId(String orderId) {
        return pingxxChargeRepository.findByOrderId(orderId).map(OrderCharge::new);
    }
}
