package com.acegear.horizon.domain.events.handlers;

import com.acegear.horizon.domain.events.*;
import com.acegear.horizon.domain.events.model.ClubManageEventBase;
import com.acegear.horizon.domain.events.model.ClubManageEventItem;
import com.acegear.horizon.domain.events.model.ClubManageEventItemGroup;
import com.acegear.horizon.domain.events.model.ClubManageEventStage;
import com.acegear.horizon.domain.models.Product;
import com.acegear.horizon.domain.models.constraint.ProductType;
import com.acegear.horizon.domain.models.jpa.*;
import com.acegear.horizon.domain.repository.ProductRepository;
import com.acegear.horizon.domain.repository.jpa.ClubBaseRepository;
import com.acegear.horizon.domain.repository.jpa.ProductActivityRepository;
import com.acegear.horizon.domain.repository.jpa.ProductBaseRepository;
import com.acegear.horizon.domain.repository.jpa.ProductItemGroupRepository;
import com.acegear.horizon.vendor.ons.ONSConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by guoweike on 17/6/28.
 */
@Component
public class ProductEventHandler {
    @Autowired
    private ONSConsumer consumer;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EventFactory eventFactory;
    @Autowired
    private ProductBaseRepository productBaseRepository;
    @Autowired
    private ProductItemGroupRepository productItemGroupRepository;
    @PostConstruct
    public void productCreateONS() {
        consumer.consume(ActivityCreateEvent.class, this::productCreate);
    }

    @PostConstruct
    public void productModifiedONS() {
        consumer.consume(ActivityModifiedEvent.class, this::productModified);
    }

    @PostConstruct
    public void productDeleteONS() {
        consumer.consume(ActivityDeleteEvent.class, this::productDelete);
    }

    @PostConstruct
    public void productItemGroupCreateONS() {
        consumer.consume(ActivityGroupCreateEvent.class, this::productItemGroupCreate);
    }


    @PostConstruct
    public void productItemGroupModifiedONS() {
        consumer.consume(ActivityGroupModifiedEvent.class, this::productItemGroupModified);
    }

    @PostConstruct
    public void productItemGroupDeleteONS() {
        consumer.consume(ActivityGroupDeleteEvent.class, this::productItemGroupDelete);
    }

    @PostConstruct
    public void productItemCreateONS() {
        consumer.consume(ActivityGoodsCreateEvent.class, this::productItemCreate);
    }


    @PostConstruct
    public void productItemModifiedONS() {
        consumer.consume(ActivityGoodsModifiedEvent.class, this::productItemModified);
    }


    @PostConstruct
    public void productItemDeleteONS() {
        consumer.consume(ActivityGoodsDeleteEvent.class, this::productItemDelete);
    }


    @PostConstruct
    public void clubCreateClubEventONS() {
        consumer.consume(ActivityCreateBackgroundEvent.class, this::clubCreateClubEvent);
    }

    @PostConstruct
    public void clubModifiedClubEventONS() {
        consumer.consume(ActivityModifiedBackgroundEvent.class, this::clubModifiedClubEvent);
    }

    public void clubCreateClubEvent(ActivityCreateBackgroundEvent activityCreateBackgroundEvent) {

        ClubManageEventBase clubEventBase = activityCreateBackgroundEvent.getEventBase();
        List<ClubManageEventStage> clubEventStages = activityCreateBackgroundEvent.getEventStages();

        Long clubEventId = clubEventBase.getClubEventId();

        List<ClubManageEventItemGroup> clubManageEventItemGroupList = new ArrayList<>();

        List<ClubManageEventItem> clubManageEventItemList = new ArrayList<>();

        productCreate(new ActivityCreateEvent(clubEventId, clubEventBase.getOwnerId(),
                clubEventBase.getName(), clubEventBase.getStartAt(), clubEventBase.getEndAt(),
                clubEventBase.getCover(),null,null, false
        ));

        for (ClubManageEventStage clubEventStage : clubEventStages) {
            List<ClubManageEventItemGroup> clubManageEventItemGroups = clubEventStage.getItemGroups();
            for (ClubManageEventItemGroup clubManageEventItemGroup : clubManageEventItemGroups) {
                clubManageEventItemGroupList.add(clubManageEventItemGroup);
                List<ClubManageEventItem> clubEventItems = clubManageEventItemGroup.getItems();
                for (ClubManageEventItem clubEventItem : clubEventItems) {
                    clubEventItem.setStageId(clubEventStage.getStageId());
                    clubManageEventItemList.add(clubEventItem);
                }
            }
        }

        for (ClubManageEventItemGroup clubManageEventItemGroup : clubManageEventItemGroupList) {
            productItemGroupCreate(new ActivityGroupCreateEvent(clubEventId, clubManageEventItemGroup.getStageId(), clubManageEventItemGroup.getGroupId(), clubManageEventItemGroup.getGroupName(), clubManageEventItemGroup.getGroupType()));
        }

        for (ClubManageEventItem clubManageEventItem : clubManageEventItemList) {
            productItemCreate(new ActivityGoodsCreateEvent(clubEventId, clubManageEventItem.getStageId(), clubManageEventItem.getGroupId(), clubManageEventItem.getItemId(), clubManageEventItem.getItemName(), clubManageEventItem.getPrice(), clubManageEventItem.getRestriction(), clubManageEventItem.getInventory()));
        }
    }


    public void clubModifiedClubEvent(ActivityModifiedBackgroundEvent activityModifiedBackgroundEvent) {
        ClubManageEventBase clubEventBase = activityModifiedBackgroundEvent.getEventBase();
        List<ClubManageEventStage> clubEventStages = activityModifiedBackgroundEvent.getEventStages();

        Long clubEventId = clubEventBase.getClubEventId();

        productModified(new ActivityModifiedEvent( clubEventId, clubEventBase.getOwnerId(),
                clubEventBase.getName(), clubEventBase.getStartAt(), clubEventBase.getEndAt(),
                clubEventBase.getCover(), null,null, false
        ));

        for (ClubManageEventStage clubEventStage : clubEventStages) {
            List<ClubManageEventItemGroup> clubEventItemGroups = clubEventStage.getItemGroups();
            for (ClubManageEventItemGroup clubEventItemGroup : clubEventItemGroups) {
                productItemGroupModified(new ActivityGroupModifiedEvent(clubEventId, clubEventStage.getStageId(), clubEventItemGroup.getGroupId(), clubEventItemGroup.getGroupName(), clubEventItemGroup.getGroupType()));
                List<ClubManageEventItem> clubEventItems = clubEventItemGroup.getItems();
                for (ClubManageEventItem clubEventItem : clubEventItems) {
                    productItemModified(new ActivityGoodsModifiedEvent(clubEventId, clubEventStage.getStageId(), clubEventItemGroup.getGroupId(), clubEventItem.getItemId(), clubEventItem.getItemName(), clubEventItem.getPrice(), clubEventItem.getRestriction(), clubEventItem.getInventory()));
                }
            }
        }
    }


    //添加活动类型商品
    public void productCreate(ActivityCreateEvent activityCreateEvent) {
        ProductBase productBase = new ProductBase(
                eventFactory.nextProductId(),
                activityCreateEvent.getName(),
                activityCreateEvent.getActivityId(),
                ProductType.EVENT_TICKET,
                null

        );

        ProductActivity productActivity = new ProductActivity(
                activityCreateEvent.getActivityId(),
                activityCreateEvent.getOwnerId(),
                activityCreateEvent.getName(),
                activityCreateEvent.getCover(),
                activityCreateEvent.getStartAt(),
                activityCreateEvent.getEndAt(),
                activityCreateEvent.isPayAudit()
        );
        Product product = new Product(productBase, productActivity);
        product.save();
    }

    //修改活动类型商品
    public void productModified(ActivityModifiedEvent activityModifiedEvent) {
        Optional<Product> productOptional = productRepository.findByRelateId(activityModifiedEvent.getActivityId());
        if (productOptional.isPresent()) {
            ProductBase productBase = productOptional.get().getProductBase();
            productBase.setName(activityModifiedEvent.getName());

            Optional<ProductActivity> productActivity = productOptional.get().getProductActivity();

            if (productActivity.isPresent()) {
                ProductActivity productActivity1 = productActivity.get();
                productActivity1.setName(activityModifiedEvent.getName());
                productActivity1.setCover(activityModifiedEvent.getCover());
                productActivity1.setEndAt(activityModifiedEvent.getEndAt());
                productActivity1.setOwnerId(activityModifiedEvent.getOwnerId());
                productActivity1.setStartAt(activityModifiedEvent.getStartAt());
                productActivity1.setPayAudit(activityModifiedEvent.isPayAudit());
            }
            Product product = new Product(productBase, productActivity.get());
            product.save();
        }
    }

    @Transactional
    public void productDelete(ActivityDeleteEvent activityDeleteEvent) {
        Optional<Product> productOptional = productRepository.findByRelateId(activityDeleteEvent.getActivityId());
        if (productOptional.isPresent()) {
            productBaseRepository.delete(productOptional.get().getProductBase());
        }
    }


    public void productItemGroupCreate(ActivityGroupCreateEvent activityGroupCreateEvent) {
        Optional<Product> product = productRepository.findByRelateId(activityGroupCreateEvent.getActivityId());
        if (product.isPresent()) {

            ProductBase productBase = product.get().getProductBase();

            ProductItemGroup productItemGroup = new ProductItemGroup(
                    activityGroupCreateEvent.getActivityGroupId(),
                    productBase.getProductId(),
                    activityGroupCreateEvent.getName(),
                    activityGroupCreateEvent.getType(),
                    null);
            List<ProductItemGroup> productItemGroups = new ArrayList<>();
            if ((productBase.getItemGroups().size() > 0) && (productBase.getItemGroups() != null)) {
                productItemGroups = productBase.getItemGroups();
            }
            productItemGroups.add(productItemGroup);
            productBase.setItemGroups(productItemGroups);
            productBaseRepository.save(productBase);
        }
    }

    @Transactional
    public void productItemGroupModified(ActivityGroupModifiedEvent activityGroupModifiedEvent) {
        Optional<Product> product = productRepository.findByRelateId(activityGroupModifiedEvent.getActivityId());
        if (product.isPresent()) {
            ProductBase productBase = product.get().getProductBase();

            List<ProductItemGroup> productItemGroups = productBase.getItemGroups();
            boolean state = false;
            for (ProductItemGroup productItemGroup : productItemGroups) {
                if (productItemGroup.getGroupId().equals(activityGroupModifiedEvent.getActivityGroupId())) {
                    productItemGroup.setGroupName(activityGroupModifiedEvent.getName());
                    productItemGroup.setGroupType(activityGroupModifiedEvent.getType());
                    state = true;
                }
            }
            if (!state) {
                ProductItemGroup productItemGroup = new ProductItemGroup(
                        activityGroupModifiedEvent.getActivityGroupId(),
                        productBase.getProductId(),
                        activityGroupModifiedEvent.getName(),
                        activityGroupModifiedEvent.getType(),
                        null
                );
                productItemGroups.add(productItemGroup);
            }
            productBaseRepository.save(productBase);
        }
    }

    @Transactional
    public void productItemGroupDelete(ActivityGroupDeleteEvent activityGroupDeleteEvent) {
        Optional<Product> product = productRepository.findByRelateId(activityGroupDeleteEvent.getActivityId());
        if (product.isPresent()) {
            ProductBase productBase = product.get().getProductBase();

            List<ProductItemGroup> productItemGroups = productBase.getItemGroups();
            Iterator<ProductItemGroup> iterator = productItemGroups.iterator();
            while (iterator.hasNext()) {
                ProductItemGroup productItemGroup = iterator.next();
                if (productItemGroup.getGroupId().equals(activityGroupDeleteEvent.getActivityGroupId())) {
                    iterator.remove();
                    break;
                }
            }
            productBaseRepository.save(productBase);
        }
    }

    public void productItemCreate(ActivityGoodsCreateEvent activityGoodsCreateEvent) {
        Optional<ProductItemGroup> productItemGroup = productItemGroupRepository.findByGroupId(activityGoodsCreateEvent.getActivityGroupId());

        if (productItemGroup.isPresent()) {
            ProductItem productItem = new ProductItem(
                    activityGoodsCreateEvent.getActivityGoodsId(),
                    activityGoodsCreateEvent.getItemName(),
                    activityGoodsCreateEvent.getPrice(),
                    activityGoodsCreateEvent.getRestriction(),
                    activityGoodsCreateEvent.getInventory());
            List<ProductItem> productItems = new ArrayList<>();
            if (productItemGroup.get().getItems() != null && productItemGroup.get().getItems().size() > 0) {
                productItems = productItemGroup.get().getItems();
            }
            productItems.add(productItem);
            productItemGroup.get().setItems(productItems);
            productItemGroupRepository.save(productItemGroup.get());
        }
    }


    @Transactional
    public void productItemModified(ActivityGoodsModifiedEvent activityGoodsModifiedEvent) {

        Optional<ProductItemGroup> productItemGroup = productItemGroupRepository.findByGroupId(activityGoodsModifiedEvent.getActivityGroupId());

        productItemGroup.ifPresent(productItemGroup1 -> {

            List<ProductItem> clubEventItemList = productItemGroup1.getItems();
            boolean state = false;
            for (ProductItem productItem : clubEventItemList) {
                if (productItem.getItemId().equals(activityGoodsModifiedEvent.getActivityGoodsId())) {
                    productItem.setPrice(activityGoodsModifiedEvent.getPrice());
                    productItem.setRestriction(activityGoodsModifiedEvent.getRestriction());
                    productItem.setInventory(activityGoodsModifiedEvent.getInventory());
                    productItem.setItemName(activityGoodsModifiedEvent.getItemName());
                    state = true;
                }
            }
            if (!state) {
                ProductItem productItem = new ProductItem(
                        activityGoodsModifiedEvent.getActivityGoodsId(),
                        activityGoodsModifiedEvent.getItemName(),
                        activityGoodsModifiedEvent.getPrice(),
                        activityGoodsModifiedEvent.getRestriction(),
                        activityGoodsModifiedEvent.getInventory()
                );
                clubEventItemList.add(productItem);
            }
            productItemGroupRepository.save(productItemGroup.get());
        });
    }

    @Transactional
    public void productItemDelete(ActivityGoodsDeleteEvent activityGoodsDeleteEvent) {
        Optional<ProductItemGroup> productItemGroup = productItemGroupRepository.findByGroupId(activityGoodsDeleteEvent.getActivityGroupId());

        productItemGroup.ifPresent(productItemGroup1 -> {

            List<ProductItem> productItems = productItemGroup1.getItems();

            Iterator<ProductItem> iterator = productItems.iterator();

            while (iterator.hasNext()) {
                ProductItem clubEventItem = iterator.next();
                if (clubEventItem.getItemId().equals(activityGoodsDeleteEvent.getActivityGoodsId())) {
                    iterator.remove();
                    break;
                }
            }
            productItemGroupRepository.save(productItemGroup.get());
        });
    }
}
