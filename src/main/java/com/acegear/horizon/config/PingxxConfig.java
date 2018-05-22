package com.acegear.horizon.config;

import com.acegear.horizon.domain.models.Order;
import com.acegear.horizon.utils.SpringUtils;
import com.pingplusplus.Pingpp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by wangsike on 2017/1/12.
 */
@Configuration
public class PingxxConfig {
    @Value("${pingxx.test_key}")
    private String pingxxTestKey;

    @Value("${pingxx.live_key}")
    private String pingxxLiveKey;

    @Value("${pingxx.env}")
    private String pingxxEnv;

    @Value("${pingxx.appId}")
    private String pingxxAppId;

    @Value("${horizon.domain.order.expireTime}")
    private Long orderExpireTime;

    @Autowired
    private SpringUtils springUtils;

    @PostConstruct
    public void getPingpp() {
        if (pingxxEnv.equals("test")) {
            Pingpp.apiKey = pingxxTestKey;
        } else {
            Pingpp.apiKey = pingxxLiveKey;
        }
        Pingpp.appId = pingxxAppId;
        try {
            Resource r = SpringUtils.getApplicationContext().getResource("classpath:res/rsa_private_key.pem");
            Scanner s = new Scanner(r.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (s.hasNextLine()) {
                sb.append(s.nextLine());
                sb.append("\n");
            }
            Pingpp.privateKey = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void order() {
        Order.expireTime = orderExpireTime;
    }
}
