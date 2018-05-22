package com.acegear.horizon.domain.events.model;


import java.util.List;

/**
 * Created by guoweike on 17/6/17.
 */
public class ClubManageEventStage {

    private Long id;

    private Long stageId;

    private Long eventId;

    private String stageName;

    private List<ClubManageEventItemGroup> itemGroups;


    private Long createAt;

    private Long updateAt;

    public ClubManageEventStage(Long id, Long stageId, Long eventId, String stageName, List<ClubManageEventItemGroup> itemGroups) {
        this.id = id;
        this.stageId = stageId;
        this.eventId = eventId;
        this.stageName = stageName;
        this.itemGroups = itemGroups;
    }

    public ClubManageEventStage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public List<ClubManageEventItemGroup> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(List<ClubManageEventItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }
}
