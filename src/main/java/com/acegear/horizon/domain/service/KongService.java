package com.acegear.horizon.domain.service;

import com.acegear.horizon.domain.models.jpa.UserToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by wangsike on 2017/1/10.
 */
@Service
public class KongService {
    @Value("${horizon.kong.server}")
    private String kongServer;

    @Value("${horizon.server}")
    private String horizonServer;

    @Value("${horizon.kong.enable}")
    private Boolean enable;

    @Value("${horizon.kong.cors}")
    private String kongCors;

    public String kongUser(UserToken userToken) {
        if (!enable) return null;
        String id = "";
        try {
            HttpResponse<JsonNode> response = Unirest.put(kongServer + "/consumers/")
                    .field("username", userToken.getId())
                    .asJson();
            id = response.getBody().getObject().getString("id");
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Boolean userExist(UserToken userToken) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(kongServer + "/consumers/" + userToken.getId())
                    .asJson();
            return response.getBody().getObject().has("id");
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addApi(String apiName) {
        if (!enable) return;
        try {
            String path = apiName;
            apiName = apiName.contains("/") ? apiName.replace("/", "-") : apiName;
            HttpResponse<JsonNode> response = Unirest.put(kongServer + "/apis/")
                    .field("name", "horizon-" + apiName)
                    .field("upstream_url", horizonServer + "/" + path)
                    .field("uris", "/" + path)
                    .field("strip_uri", true)
                    .asJson();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void addCors(String apiName) {
        if (!enable) return;
        try {
            String path = apiName;
            apiName = apiName.contains("/") ? apiName.replace("/", "-") : apiName;
            HttpResponse<JsonNode> response = Unirest.post(kongServer + "/apis/horizon-" + apiName + "/plugins")
                    .field("name", "cors")
                    .field("config.origins", kongCors)
                    .asJson();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }


    public void deleteApi(String apiName) {
        if (!enable) return;
        try {
            apiName = apiName.contains("/") ? apiName.replace("/", "-") : apiName;
            HttpResponse<JsonNode> response = Unirest.delete(kongServer + "/apis/horizon-" + apiName)
                    .asJson();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteConsumers(UserToken userToken) {
        if (!enable) return;
        try {
            HttpResponse<JsonNode> response = Unirest.delete(kongServer + "/consumers/" + userToken.getId())
                    .asJson();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void addApiJwt(String apiName, String anonymousId) {
        if (!enable) return;
        try {
            apiName = apiName.contains("/") ? apiName.replace("/", "-") : apiName;
            HttpResponse<JsonNode> response = Unirest.put(kongServer + "/apis/horizon-" + apiName + "/plugins")
                    .field("name", "jwt")
                    .field("config.anonymous", anonymousId)
                    .asJson();
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean apiExist(String apiName) {
        try {
            apiName = apiName.contains("/") ? apiName.replace("/", "-") : apiName;
            HttpResponse<JsonNode> response = Unirest.get(kongServer + "/apis/horizon-" + apiName)
                    .asJson();
            return response.getBody().getObject().has("id");
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getConsumersId(String anonymousUserName) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(kongServer + "/consumers/" + anonymousUserName)
                    .asJson();
            return response.getBody().getObject().getString("id");
        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
