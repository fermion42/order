package com.acegear.horizon.domain.repository.jpa;

import com.acegear.horizon.domain.models.jpa.ProductBase;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by guoweike on 17/6/28.
 */
public interface ProductBaseRepository extends PagingAndSortingRepository<ProductBase, Long>{
Optional<ProductBase> findByProductId(Long productId);
    Optional<ProductBase> findByRelateId(Long relateId);

}
