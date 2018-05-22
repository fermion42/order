package com.acegear.horizon.domain.events;

import com.acegear.horizon.domain.models.jpa.ContentPart;

import java.util.List;

/**
 * Created by guoweike on 17/7/7.
 */
public class ClubCreateEvent extends BaseEvent {

    private Long clubId;

    private Long userId;

    private String name;
    private String address;
    private String phone;
    private Boolean owner;
    private String imgUrl;
    private String coverUrl;

    private String summary;

    private List<ContentPart> introduction;

    private boolean joinVerify;

    public ClubCreateEvent(Long clubId, Long userId, String name, String address, String phone,
                             Boolean owner, String imgUrl, String coverUrl, String summary,
                             List<ContentPart> contentParts,boolean joinVerify) {

        super();
        this.clubId = clubId;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.owner = owner;
        this.imgUrl = imgUrl;
        this.coverUrl = coverUrl;
        this.summary = summary;
        this.introduction = contentParts;
        this.joinVerify = joinVerify;
        genEventId();

    }


    public ClubCreateEvent() {
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<ContentPart> getIntroduction() {
        return introduction;
    }

    public boolean isJoinVerify() {
        return joinVerify;
    }

    public void setJoinVerify(boolean joinVerify) {
        this.joinVerify = joinVerify;
    }

    public void setIntroduction(
            List<ContentPart> introduction) {
        this.introduction = introduction;
    }

    public String routingKey() {
        return "horizon.domain.club.update";
    }
}



