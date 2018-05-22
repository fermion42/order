package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.ProductItemGroup;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by guoweike on 17/6/28.
 */
public interface ProductItemGroupRepository extends PagingAndSortingRepository<ProductItemGroup, Long> {
    Optional<ProductItemGroup> findByGroupId(Long groupId);
}
