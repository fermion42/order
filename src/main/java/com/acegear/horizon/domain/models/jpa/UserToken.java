package com.acegear.horizon.domain.models.jpa;

import com.acegear.horizon.domain.models.constraint.LoginPlatform;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import javax.persistence.Id;

@Document(collection = "UserToken")
public class UserToken {
    @Id
    private String id;

    private Long userId;

    private String accessToken;

    private LoginPlatform platform;

    public UserToken(Long userId, LoginPlatform platform) {
        this.userId = userId;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(LoginPlatform platform) {
        this.platform = platform;
    }

    public void newToken() {
        setAccessToken(UUID.randomUUID().toString());
    }
}
