package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.constraint.ProductActivityState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by guoweike on 17/6/29.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column
    private Long activityId;
    @Column
    private Long ownerId;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ProductActivityState state;

    @Column
    private String name;

    @Column
    private String cover;

    @Column
    private Long startAt;

    @Column
    private Long endAt;
    @Column
    private boolean payAudit;

    @CreatedDate
    @Column
    private Long createAt;

    @LastModifiedDate
    @Column
    private Long updateAt;


    public ProductActivity(Long activityId, Long ownerId, String name, String cover,  Long startAt, Long endAt, boolean payAudit) {
        this.activityId = activityId;
        this.ownerId = ownerId;
        this.state = ProductActivityState.SIGNUP;
        this.name = name;
        this.cover = cover;
        this.startAt = startAt;
        this.endAt = endAt;
        this.payAudit = payAudit;
    }

    public ProductActivity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public ProductActivityState getState() {
        Long current = System.currentTimeMillis();
        if (current < endAt) {
            state = ProductActivityState.SIGNUP;
        } else {
            state = ProductActivityState.END;
        }
        return state;
    }

    public void setState(ProductActivityState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }


    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public boolean isPayAudit() {
        return payAudit;
    }

    public void setPayAudit(boolean payAudit) {
        this.payAudit = payAudit;
    }
}
