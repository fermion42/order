package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.ProductItem;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by guoweike on 17/6/28.
 */
public interface ProductItemRepository extends PagingAndSortingRepository<ProductItem, Long> {
    Optional<ProductItem> findByItemId(Long itemId);
}
