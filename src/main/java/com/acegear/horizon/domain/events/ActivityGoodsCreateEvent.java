package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/6/28.
 */
public class ActivityGoodsCreateEvent extends BaseEvent {

    private Long activityId;

    //活动阶段id
    private Long activityStageId;

    //活动分组id
    private Long activityGroupId;

    //活动项目id
    private Long activityGoodsId;

    private String itemName;

    private Long price;

    private int restriction;

    private int inventory;

    public ActivityGoodsCreateEvent(Long activityId, Long activityStageId, Long activityGroupId,
                                    Long activityGoodsId, String itemName, Long price,
                                    int restriction, int inventory) {
        super();
        this.activityId = activityId;
        this.activityStageId = activityStageId;
        this.activityGroupId = activityGroupId;
        this.activityGoodsId = activityGoodsId;
        this.itemName = itemName;
        this.price = price;
        this.restriction = restriction;
        this.inventory = inventory;
        genEventId();
    }


    public ActivityGoodsCreateEvent() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }


    public Long getActivityGroupId() {
        return activityGroupId;
    }

    public void setActivityGroupId(Long activityGroupId) {
        this.activityGroupId = activityGroupId;
    }

    public Long getActivityGoodsId() {
        return activityGoodsId;
    }

    public void setActivityGoodsId(Long activityGoodsId) {
        this.activityGoodsId = activityGoodsId;
    }

    public Long getActivityStageId() {
        return activityStageId;
    }

    public void setActivityStageId(Long activityStageId) {
        this.activityStageId = activityStageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getRestriction() {
        return restriction;
    }

    public void setRestriction(int restriction) {
        this.restriction = restriction;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.activity.goods.new";
    }
}

