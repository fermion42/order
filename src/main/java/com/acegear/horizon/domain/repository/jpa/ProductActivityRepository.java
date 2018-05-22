package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.ProductActivity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by guoweike on 17/6/29.
 */
public interface ProductActivityRepository extends PagingAndSortingRepository<ProductActivity, Long>{
    Optional<ProductActivity> findByActivityId(Long activityId);

    Optional<ProductActivity> findByOwnerId(Long ownerId);
}
