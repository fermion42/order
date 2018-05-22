package com.acegear.horizon.controller.vo;

import java.util.HashMap;

/**
 * Created by wangsike on 2016/12/15.
 */
public class ResultVO<T> {

    private String message;

    private Integer code;

    private T body;

    public ResultVO() {
    }

    public final static ResultVO ORDER_CANT_DELETE = error("order can`t delete", 2027);
    public final static ResultVO ORDER_NOT_FOUND = error("order_not_found", 2024);
    public final static ResultVO ORDER_CONSUMED = error("order consumed", 2025);
    public final static ResultVO ORDER_CANT_CANCEL = error("order can`t cancel", 2026);
    public final static ResultVO ORDER_ITEMS_VERIFY_FAILED = error("order_items_verify_failed", 2021);
    public final static ResultVO ORDER_UNSETTLED_FAILED = error("order_unsettled_failed", 2022);
    public final static ResultVO ORDER_PAYED_FAILED = error("order_payed_failed", 2023);

    public final static ResultVO REQUEST_OVER_RATE_LIMIT = error("request over rate limit", 2030);
    public final static ResultVO API_ERROR = error("api error", 2031);
    public final static ResultVO CHANNEL_ERROR = error("channel error", 2032);
    public final static ResultVO INVALID_REQUEST = error("invalid request", 2033);
    public final static ResultVO API_CONNECTION_ERROR = error("api connection error", 2034);
    public final static ResultVO AUTH_ERROR = error("auth error", 2035);

    public final static ResultVO EMPTY = new ResultVO<>(new HashMap<>());

    public static ResultVO error(String message, Integer code) {
        return new ResultVO<>(message, code, null);
    }

    public ResultVO(String message, Integer code, T body) {
        this.message = message;
        this.code = code;
        this.body = body;
    }

    public ResultVO(T body) {
        this.body = body;
        message = "";
        code = 0;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
