package com.acegear.horizon.domain.models.vo;

/**
 * Created by wangsike on 2017/1/17.
 */
public class EventVO {
    private String name;
    private String cover;
    private Long clubId;
    private String clubName;
    private String clubLogo;

    public EventVO() {
    }

    public EventVO(String name, String cover, Long clubId, String clubName, String clubLogo) {
        this.name = name;
        this.cover = cover;
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubLogo = clubLogo;
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

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }
}
