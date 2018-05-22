package com.acegear.horizon.domain.events;

/**
 * Created by guoweike on 17/1/24.
 */
//活动项目删除
public class ActivityGoodsDeleteEvent extends BaseEvent {

    private Long activityId;

    //活动阶段id
    private Long activityStageId;

    //活动分组id
    private Long activityGroupId;

    //活动项目id
    private Long activityGoodsId;


    public ActivityGoodsDeleteEvent(Long activityId, Long activityStageId, Long activityGroupId,
                                    Long activityGoodsId) {
        super();
        this.activityId = activityId;
        this.activityStageId = activityStageId;
        this.activityGroupId = activityGroupId;
        this.activityGoodsId = activityGoodsId;
        genEventId();
    }

    public ActivityGoodsDeleteEvent() {
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

    @Override
    public String routingKey() {
        return "horizon.domain.activity.goods.delete";
    }
}
