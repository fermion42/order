package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.constraint.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wangsike on 2017/5/9.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    private Long productId;

    @Column
    private String name;

    @Column
    private Long relateId;

    @Column
    private ProductType type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductItemGroup> itemGroups;

    @CreatedDate
    @Column
    private Long createAt;

    @LastModifiedDate
    @Column
    private Long updateAt;

    public ProductBase() {
    }


    public ProductBase(Long productId, String name, Long relateId, ProductType type, List<ProductItemGroup> itemGroups) {
        this.productId = productId;
        this.name = name;
        this.relateId = relateId;
        this.type = type;
        this.itemGroups = itemGroups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<ProductItemGroup> getItemGroups() {
        return itemGroups;
    }

    public void setItemGroups(List<ProductItemGroup> itemGroups) {
        this.itemGroups = itemGroups;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

}
