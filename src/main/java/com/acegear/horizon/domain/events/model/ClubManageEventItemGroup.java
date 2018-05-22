package com.acegear.horizon.domain.events.model;

import com.acegear.horizon.domain.models.constraint.ItemGroupType;

import java.util.List;

/**
 * Created by guoweike on 17/6/17.
 */
public class ClubManageEventItemGroup {


    private Long id;

    private Long groupId;

    private Long stageId;

    private String groupName;


    private ItemGroupType groupType;


    private List<ClubManageEventItem> items;


    private Long createAt;

    private Long updateAt;

    public ClubManageEventItemGroup(Long id, Long groupId, Long stageId, String groupName, ItemGroupType groupType, List<ClubManageEventItem> items) {
        this.id = id;
        this.groupId = groupId;
        this.stageId = stageId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.items = items;
    }

    public ClubManageEventItemGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ItemGroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(ItemGroupType groupType) {
        this.groupType = groupType;
    }

    public List<ClubManageEventItem> getItems() {
        return items;
    }

    public void setItems(List<ClubManageEventItem> items) {
        this.items = items;
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
