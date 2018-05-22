package com.acegear.horizon.domain.models;

import com.acegear.horizon.domain.models.jpa.*;
import com.acegear.horizon.domain.repository.jpa.*;
import com.acegear.horizon.utils.SpringUtils;
import java.util.Optional;


/**
 * Created by guoweike on 17/6/29.
 */
public class Product {

    private ProductBase productBase;
    private ProductActivity productActivity;

    private ProductBaseRepository productBaseRepository;

    private ProductItemRepository productItemRepository;

    private ProductActivityRepository productActivityRepository;

    private EventOrderRepository eventOrderRepository;

    private ClubBaseRepository clubBaseRepository;


    private void init() {
        productBaseRepository = SpringUtils.getBean(ProductBaseRepository.class);
        productActivityRepository = SpringUtils.getBean(ProductActivityRepository.class);
        productItemRepository = SpringUtils.getBean(ProductItemRepository.class);
        eventOrderRepository = SpringUtils.getBean(EventOrderRepository.class);
        clubBaseRepository = SpringUtils.getBean(ClubBaseRepository.class);
    }

    public Product() {
        init();
    }

    public Product(ProductBase productBase) {
        init();
        this.productBase = productBase;
    }

    public Product save() {
        productBase = productBaseRepository.save(productBase);

        if (productActivity != null) {
            productActivity = productActivityRepository.save(productActivity);
        }
        return this;
    }

    public Product(ProductBase productBase, ProductActivity productActivity) {
        init();
        this.productBase = productBase;
        this.productActivity = productActivity;
    }

    public Optional<ProductActivity> getProductActivity() {
        return productActivityRepository.findByActivityId(productBase.getRelateId());
    }

    public Optional<ClubBase> getClub(Long clubId) {
        return clubBaseRepository.findByClubId(clubId);
    }

    public ProductBase getProductBase() {
        return productBase;
    }

}
