package com.acegear.horizon.domain.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by guoweike on 17/7/7.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ClubBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private Long clubId;

    @Column
    private String clubName;

    @Column
    private String logo;

    @Column
    private String background;


    public ClubBase() {
    }

    public ClubBase(Long clubId, String clubName, String logo, String background) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.logo = logo;
        this.background = background;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
