package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by wangsike on 2017/1/11.
 */
public interface EventOrderRepository extends PagingAndSortingRepository<EventOrder, Long>, JpaSpecificationExecutor<EventOrder> {
    Optional<EventOrder> findByOrderId(String orderId);

    List<EventOrder> findByEventId(Long eventId, Pageable pageable);

    List<EventOrder> findByEventId(Long eventId);


    @Query("select eo from EventOrder eo join eo.items ei where ei.itemId = ?1")
    List<EventOrder> findByItemId(Long itemId, Pageable pageable);

    List<EventOrder> findByEventIdAndState(Long eventId,OrderState orderState);

}
