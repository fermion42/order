package com.acegear.horizon.domain.service;

import com.acegear.horizon.domain.models.Order;
import com.acegear.horizon.domain.models.PayChannel;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pingplusplus.Pingpp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class OrderService {
    @Value("${horizon.server}")
    private String horizonServer;

    public Map<String, Object> getChargeParams(Order order, PayChannel channel, Long userId) {
        EventOrder eventOrder = order.getEventOrder();
        Map<String, Object> chargeParams = new HashMap<>();
        String subject = eventOrder.getEventName().replace("\n", "");
        subject = subject.length() > 32 ? subject.substring(0, 32) : subject;
        chargeParams.put("amount", eventOrder.getTotalPrice());//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
        chargeParams.put("currency", "cny");
        chargeParams.put("subject", subject);
        chargeParams.put("body", "empty");
        chargeParams.put("order_no", eventOrder.getOrderId());// 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeParams.put("channel", channel.getChannel());// 支付使用的第三方支付渠道取值，请参考：https://www.pingxx.com/api#api-c-new
        chargeParams.put("client_ip", "127.0.0.1"); // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        chargeParams.put("extra", channel.getExtra(userId));
        Map<String, String> app = new HashMap<>();
        app.put("id", Pingpp.appId);
        chargeParams.put("app", app);
        return chargeParams;
    }

    public String getOpenId(Long userId, Integer type) {
        String url = horizonServer + "/users/" + userId + "/wechat/" + type;
        try {
            return Unirest
                    .get(url)
                    .asJson()
                    .getBody()
                    .getObject()
                    .getJSONObject("body")
                    .getString("openId");
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getQrUrl(Order order) {
        String path = horizonServer + "/qrcode/" + order.getOrderId();
        try {
            return Unirest.put(path)
                          .asJson()
                          .getBody()
                          .getObject().getString("url");
        } catch (UnirestException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Boolean verify(Order order,Long userId, String signature) {
        //TODO 向 app 服务器确认该用户是否有权限操作订单
        String path = horizonServer + "/qrcode/" + order.getOrderId();
        try {
            return Unirest.get(path)
                          .queryString("signature", signature)
                          .asJson()
                          .getBody()
                          .getObject()
                          .getBoolean("check");
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }
}
