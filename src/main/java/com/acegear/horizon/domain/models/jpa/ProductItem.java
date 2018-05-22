package com.acegear.horizon.domain.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by wangsike on 2017/5/9.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private Long itemId;

    @Column
    private String itemName;

    @Column
    private Long price;

    @Column
    private Integer restriction;

    @Column
    private Integer inventory;

    @CreatedDate
    @Column
    private Long createAt;

    @LastModifiedDate
    @Column
    private Long updateAt;

    public ProductItem() {
    }

    public ProductItem(Long itemId, String itemName, Long price, Integer restriction, Integer inventory) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.restriction = restriction;
        this.inventory = inventory;
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

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void deduct(Integer count) {
        if (canDeduct(count)) {
            setInventory(inventory - count);
        }
    }

    public Boolean canDeduct(Integer count) {
        if (restriction != 0 && count > restriction) {
            return false;
        } else if (count > inventory) {
            return false;
        } else {
            return true;
        }
    }
    public void undo(Integer count) {
        setInventory(inventory + count);
    }

}
