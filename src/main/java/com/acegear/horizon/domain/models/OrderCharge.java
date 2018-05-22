package com.acegear.horizon.domain.models;

import com.acegear.horizon.domain.models.jpa.PingxxCharge;
import com.acegear.horizon.domain.repository.jpa.PingxxChargeRepository;
import com.acegear.horizon.utils.SpringUtils;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.exception.RateLimitException;
import com.pingplusplus.model.Charge;

import java.util.Map;
import java.util.Optional;

/**
 * Created by wangsike on 2017/1/12.
 */
public class OrderCharge {
    private PingxxCharge pingxxCharge;

    private PingxxChargeRepository pingxxChargeRepository;

    private void init() {
        pingxxChargeRepository = SpringUtils.getBean(PingxxChargeRepository.class);
    }

    public OrderCharge(PingxxCharge pingxxCharge) {
        init();
        this.pingxxCharge = pingxxCharge;
    }

    public static Optional<OrderCharge> create(Map<String, Object> chargeParams) {
        try {
            Charge c = Charge.create(chargeParams);
            OrderCharge orderCharge = new OrderCharge(new PingxxCharge(c));
            return Optional.of(orderCharge.save());
        } catch (AuthenticationException | InvalidRequestException | APIConnectionException | APIException | ChannelException | RateLimitException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public OrderCharge save() {
        Optional<PingxxCharge> chargeOptional = pingxxChargeRepository.findByChargeId(pingxxCharge.getChargeId());
        if (!chargeOptional.isPresent()) {
            pingxxCharge = pingxxChargeRepository.save(pingxxCharge);
        }
        return this;
    }

    public void updateCharge(Charge charge) {
        pingxxCharge.setChargeJson(charge.toString());
        save();
    }

    public PingxxCharge getPingxxCharge() {
        return pingxxCharge;
    }

    public void setPingxxCharge(PingxxCharge pingxxCharge) {
        this.pingxxCharge = pingxxCharge;
    }
}
