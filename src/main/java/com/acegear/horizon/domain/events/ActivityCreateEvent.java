package com.acegear.horizon.domain.events;


/**
 * Created by guoweike on 17/6/28.
 */
public class ActivityCreateEvent extends BaseEvent {

    private Long activityId;

    private Long ownerId;

    private String name;

    private Long startAt;

    private boolean payAudit;

    private Long endAt;

    private String cover;

    private String logo;//俱乐部logo
    private String clubName;//俱乐部名

    public ActivityCreateEvent(Long activityId, Long ownerId, String name, Long startAt, Long endAt, String cover, String logo, String clubName,boolean payAudit) {
        super();
        this.activityId = activityId;
        this.ownerId = ownerId;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cover = cover;
        this.logo = logo;
        this.clubName = clubName;
        this.payAudit = payAudit;
        genEventId();
    }

    public ActivityCreateEvent() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isPayAudit() {
        return payAudit;
    }

    public void setPayAudit(boolean payAudit) {
        this.payAudit = payAudit;
    }

    @Override
    public String routingKey() {
        return "horizon.domain.activity.new";
    }
}

