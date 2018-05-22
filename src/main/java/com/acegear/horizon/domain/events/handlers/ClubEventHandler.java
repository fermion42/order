package com.acegear.horizon.domain.events.handlers;

import com.acegear.horizon.domain.events.ActivityEndEvent;
import com.acegear.horizon.domain.events.ClubCreateEvent;
import com.acegear.horizon.domain.events.ClubModifiedEvent;
import com.acegear.horizon.domain.models.OrderState;
import com.acegear.horizon.domain.models.jpa.ClubBase;
import com.acegear.horizon.domain.models.jpa.EventOrder;
import com.acegear.horizon.domain.repository.jpa.ClubBaseRepository;
import com.acegear.horizon.domain.repository.jpa.EventOrderRepository;
import com.acegear.horizon.vendor.ons.ONSConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Created by guoweike on 17/7/7.
 */
@Component
public class ClubEventHandler {

    @Autowired
    private ONSConsumer consumer;
    @Autowired
    private ClubBaseRepository clubBaseRepository;
    @Autowired
    private EventOrderRepository eventOrderRepository;

    @PostConstruct
    public void updateClubONS() {
        consumer.consume(ClubModifiedEvent.class, this::updateClub);
    }

    @PostConstruct
    public void createClubONS() {
        consumer.consume(ClubCreateEvent.class, this::createClub);
    }

    @PostConstruct
    public void activityEndEventONS() {
        consumer.consume(ActivityEndEvent.class, this::activityEndEvent);
    }


    public void createClub(ClubCreateEvent clubCreateEvent) {
        ClubBase clubBase = new ClubBase(clubCreateEvent.getClubId(), clubCreateEvent.getName(), clubCreateEvent.getImgUrl(), clubCreateEvent.getCoverUrl());
        clubBaseRepository.save(clubBase);
    }


    public void updateClub(ClubModifiedEvent clubModifiedEvent) {
        Optional<ClubBase> clubBaseOptional = clubBaseRepository.findByClubId(clubModifiedEvent.getClubId());
        clubBaseOptional.ifPresent(clubBase -> {
                    clubBase.setClubName(clubModifiedEvent.getName());
                    clubBase.setLogo(clubModifiedEvent.getImgUrl());
                    clubBase.setBackground(clubModifiedEvent.getCoverUrl());
                    clubBaseRepository.save(clubBase);
                }
        );
    }

    public void activityEndEvent(ActivityEndEvent activityEndEvent) {
        List<EventOrder> eventOrders = eventOrderRepository.findByEventIdAndState(activityEndEvent.getEventId(), OrderState.PAYED);
        for (EventOrder eventOrder : eventOrders) {
            eventOrder.setState(OrderState.CONSUMED);
            eventOrderRepository.save(eventOrder);
        }
    }
}
