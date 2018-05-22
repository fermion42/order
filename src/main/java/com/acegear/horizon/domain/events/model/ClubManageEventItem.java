package com.acegear.horizon.domain.events.model;



/**
 * Created by guoweike on 17/6/17.
 */
public class ClubManageEventItem {

    private Long id;

    private Long itemId;

    private String itemName;

    private Long price;

    private Integer restriction;

    private Integer inventory;

    private Long version;

    private Long createAt;

    private Long updateAt;

    private Long groupId;

    private Long stageId;

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

    public ClubManageEventItem(Long id, Long itemId, String itemName, Long price, Integer restriction, Integer inventory, Long version,  Long groupId, Long stageId) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.restriction = restriction;
        this.inventory = inventory;
        this.version = version;
        this.groupId = groupId;
        this.stageId = stageId;
    }

    public ClubManageEventItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getRestriction() {
        return restriction;
    }

    public void setRestriction(Integer restriction) {
        this.restriction = restriction;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
}
