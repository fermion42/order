package com.acegear.horizon.domain.events.model;

/**
 * Created by guoweike on 17/6/17.
 */
public class ClubManageEventBase {

    private Long id;

    private Long clubEventId;

    private Long ownerId;

    private String name;

    private String servicePhone;

    private Long startAt;

    private Long endAt;

    private String cover;

    public ClubManageEventBase() {
    }

    public ClubManageEventBase(Long id, Long clubEventId, Long ownerId, String name, String servicePhone, Long startAt, Long endAt, String cover) {
        this.id = id;
        this.clubEventId = clubEventId;
        this.ownerId = ownerId;
        this.name = name;
        this.servicePhone = servicePhone;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClubEventId() {
        return clubEventId;
    }

    public void setClubEventId(Long clubEventId) {
        this.clubEventId = clubEventId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


}
