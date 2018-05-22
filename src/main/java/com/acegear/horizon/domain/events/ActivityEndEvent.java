package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/1/23.
 */
public class ActivityEndEvent extends BaseEvent {


    private Long activityId;

    public ActivityEndEvent(Long activityId) {
        super();
        this.activityId = activityId;
        genEventId();
    }

    public ActivityEndEvent() {
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.activity.end";
    }
}
