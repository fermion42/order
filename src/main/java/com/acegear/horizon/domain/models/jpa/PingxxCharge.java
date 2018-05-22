package com.acegear.horizon.domain.models.jpa;

import com.pingplusplus.model.Charge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by wangsike on 2017/1/12.
 */
@Entity
public class PingxxCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String chargeJson;

    @Column(unique = true)
    private String orderId;

    @Column(unique = true)
    private String chargeId;

    public PingxxCharge() {
    }

    public PingxxCharge(Charge charge) {
        this.chargeJson = charge.toString();
        this.orderId = charge.getOrderNo();
        this.chargeId = charge.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargeJson() {
        return chargeJson;
    }

    public void setChargeJson(String chargeJson) {
        this.chargeJson = chargeJson;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}
