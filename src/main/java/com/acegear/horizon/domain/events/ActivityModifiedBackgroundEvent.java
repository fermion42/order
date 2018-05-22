package com.acegear.horizon.domain.events;

import com.acegear.horizon.domain.events.model.ClubManageEventBase;
import com.acegear.horizon.domain.events.model.ClubManageEventStage;

import java.util.List;

/**
 * Created by guoweike on 17/7/7.
 */
public class ActivityModifiedBackgroundEvent  extends BaseEvent {
    private ClubManageEventBase eventBase; // 活动基础信息
    private List<ClubManageEventStage> eventStages; // 阶段列表

    public ActivityModifiedBackgroundEvent() {
    }

    public ActivityModifiedBackgroundEvent(ClubManageEventBase eventBase, List<ClubManageEventStage> eventStages) {
        this.eventBase = eventBase;
        this.eventStages = eventStages;
    }

    public ClubManageEventBase getEventBase() {
        return eventBase;
    }

    public void setEventBase(ClubManageEventBase eventBase) {
        this.eventBase = eventBase;
    }



    public List<ClubManageEventStage> getEventStages() {
        return eventStages;
    }

    public void setEventStages(List<ClubManageEventStage> eventStages) {
        this.eventStages = eventStages;
    }



    @Override
    public String routingKey() {
        return "horizon.domain.activity.background.update";
    }
}
