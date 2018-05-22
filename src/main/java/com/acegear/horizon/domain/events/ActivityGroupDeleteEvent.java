package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/1/24.
 */
//活动分组删除
public class ActivityGroupDeleteEvent extends BaseEvent {

    private Long activityId;
    //活动阶段id
    private Long activityStageId;
    //活动分组id
    private Long activityGroupId;

    public ActivityGroupDeleteEvent(Long activityId, Long activityStageId, Long activityGroupId) {
        super();
        this.activityId = activityId;
        this.activityStageId = activityStageId;
        this.activityGroupId = activityGroupId;
        genEventId();
    }

    public ActivityGroupDeleteEvent() {
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


    @Override
    public String routingKey() {
        return "horizon.domain.activity.group.delete";
    }
}
