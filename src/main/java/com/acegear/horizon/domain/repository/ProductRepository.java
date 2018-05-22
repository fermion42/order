package com.acegear.horizon.domain.repository;

import com.acegear.horizon.domain.models.Product;
import com.acegear.horizon.domain.models.jpa.ProductActivity;
import com.acegear.horizon.domain.repository.jpa.ProductActivityRepository;
import com.acegear.horizon.domain.repository.jpa.ProductBaseRepository;
import com.acegear.horizon.domain.repository.jpa.ProductItemGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by guoweike on 17/6/29.
 */
@Component
public class ProductRepository {
    @Autowired
    private ProductBaseRepository productBaseRepository;
    @Autowired
    private ProductItemGroupRepository productItemGroupRepository;
    @Autowired
    private ProductActivityRepository productActivityRepository;


    public Optional<Product> findByProductId(Long productId) {
        Optional<Product> optional = productBaseRepository.findByProductId(productId).map(Product::new);
        return optional;
    }

    public Optional<Product> findByRelateId(Long relateId) {
        Optional<Product> optional = productBaseRepository.findByRelateId(relateId).map(Product::new);
        return optional;
    }

    public Optional<ProductActivity> findByActivityId(Long activityId) {
        Optional<ProductActivity> optional = productActivityRepository.findByActivityId(activityId);
        return optional;
    }


}
