package com.acegear.horizon.domain.events;

import com.acegear.horizon.domain.models.constraint.ItemGroupType;

/**
 * Created by guoweike on 17/6/28.
 */
public class ActivityGroupCreateEvent extends BaseEvent {

    private Long activityId;
    //活动阶段id
    private Long activityStageId;
    //活动分组id
    private Long activityGroupId;

    private String name;

    private ItemGroupType type;


    public ActivityGroupCreateEvent(Long activityId, Long activityStageId, Long activityGroupId,
                                    String name, ItemGroupType type) {
        super();
        this.activityId = activityId;
        this.activityStageId = activityStageId;
        this.activityGroupId = activityGroupId;
        this.name = name;
        this.type = type;
        genEventId();
    }


    public ActivityGroupCreateEvent() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityStageId() {
        return activityStageId;
    }

    public void setActivityStageId(Long activityStageId) {
        this.activityStageId = activityStageId;
    }

    public Long getActivityGroupId() {
        return activityGroupId;
    }

    public void setActivityGroupId(Long activityGroupId) {
        this.activityGroupId = activityGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemGroupType getType() {
        return type;
    }

    public void setType(ItemGroupType type) {
        this.type = type;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.activity.group.new";
    }

}

