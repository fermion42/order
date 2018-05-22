package com.acegear.horizon;

import com.acegear.horizon.domain.repository.OrderRepository;
import com.acegear.horizon.domain.models.constraint.LoginPlatform;
import com.acegear.horizon.domain.models.jpa.UserToken;
import com.acegear.horizon.domain.repository.jpa.UserTokenRepository;
import com.acegear.horizon.domain.service.KongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangsike on 2017/1/16.
 */
@Component
public class InitBean {
    @Autowired
    private KongService kongService;
    @Autowired
    private OrderRepository orderRepository;
    @Value("${initClubEventEnd}")
    private boolean initClubEventEnd;
    @Autowired
    private UserTokenRepository userTokenRepository;

    @PostConstruct
    public void initOrders() {
        String apiName = "orders";

        UserToken userToken = userTokenRepository.findTopByUserId(0L);
        if (userToken == null) {
            userToken = new UserToken(0L, LoginPlatform.APP);
            userToken = userTokenRepository.save(userToken);
        }
        String anonymousId;
        if (kongService.userExist(userToken)) {
            anonymousId = kongService.getConsumersId(userToken.getId());
        } else {
            anonymousId = kongService.kongUser(userToken);
        }
        if (kongService.apiExist(apiName)) {
            kongService.deleteApi(apiName);
        }
        kongService.addApi(apiName);
        kongService.addCors(apiName);
        kongService.addApiJwt(apiName, anonymousId);
    }

    @PostConstruct
    public void initCallback() {
        String apiName = "orders/callback";
        if (kongService.apiExist(apiName)) {
            kongService.deleteApi(apiName);
        }
        kongService.addCors(apiName);
        kongService.addApi(apiName);
    }

    @PostConstruct
    public void initQuery() {
        String apiName = "orders/query";
        if (kongService.apiExist(apiName)) {
            kongService.deleteApi(apiName);
        }
        kongService.addCors(apiName);
        kongService.addApi(apiName);
    }

    @PostConstruct
    public void initClubEventEnd() {
        if (initClubEventEnd) {
            orderRepository.clubEventEnd();
        }
    }


}
