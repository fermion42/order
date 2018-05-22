package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/3/4.
 */
public class ActivityDeleteEvent extends BaseEvent {

    private Long activityId;

    public ActivityDeleteEvent(
            Long activityId) {
        super();
        this.activityId = activityId;
        genEventId();
    }

    public ActivityDeleteEvent() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.activity.delete";
    }

}
