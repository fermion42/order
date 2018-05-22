package com.acegear.horizon.domain.models;

import com.acegear.horizon.domain.service.OrderService;
import com.acegear.horizon.utils.SpringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangsike on 2017/1/12.
 */
public enum PayChannel {
    ALIPAY_APP, WX_APP, APPLEPAY, WX_PUB;

    public String getChannel() {
        switch (this) {
            case ALIPAY_APP:
                return "alipay";
            case APPLEPAY:
                return "applepay_upcap";
            case WX_APP:
                return "wx";
            case WX_PUB:
                return "wx_pub";
            default:
                return "";
        }
    }

    public Map<String, String> getExtra(Long userId) {
        Map<String, String> result = new HashMap<>();
        switch (this) {
            case WX_PUB:
                String openId = SpringUtils
                        .getBean(OrderService.class)
                        .getOpenId(userId, 2);
                result.put("open_id", openId);
                return result;
            default:
                return result;
        }
    }
}
