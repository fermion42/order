package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.constraint.ItemGroupType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Created by wangsike on 2017/5/9.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductItemGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private Long groupId;

    @Column
    private Long productId;

    @Column
    private String groupName;

    @Column
    @Enumerated
    private ItemGroupType groupType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductItem> items;

    @CreatedDate
    @Column
    private Long createAt;

    @LastModifiedDate
    @Column
    private Long updateAt;

    public ProductItemGroup() {
    }

    public ProductItemGroup(Long groupId, Long productId, String groupName, ItemGroupType groupType, List<ProductItem> items) {
        this.groupId = groupId;
        this.productId = productId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.items = items;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public List<ProductItem> getItems() {
        return items;
    }

    public void setItems(List<ProductItem> items) {
        this.items = items;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }
}
