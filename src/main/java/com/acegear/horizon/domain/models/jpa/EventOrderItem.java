package com.acegear.horizon.domain.models.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by wangsike on 2017/1/11.
 */
@Entity
public class EventOrderItem {
    @Column
    private Long itemId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String itemName;

    @Column
    private Long price;

    @Column
    private Integer count;

    public EventOrderItem() {

    }

    public EventOrderItem(Long itemId, String itemName, Long price, Integer count) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getItemPrice() {
        return price * count;
    }
}
