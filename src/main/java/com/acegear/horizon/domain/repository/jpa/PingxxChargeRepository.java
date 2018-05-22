package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.PingxxCharge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by wangsike on 2017/1/12.
 */
public interface PingxxChargeRepository extends PagingAndSortingRepository<PingxxCharge, Long>, JpaSpecificationExecutor<PingxxCharge> {
    Optional<PingxxCharge> findByChargeId(String chargeId);

    Stream<PingxxCharge> findByOrderId(String orderId);
}
