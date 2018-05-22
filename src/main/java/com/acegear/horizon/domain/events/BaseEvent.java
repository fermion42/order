package com.acegear.horizon.domain.events;

import com.acegear.horizon.utils.SpringUtils;


/**
 * Created by guoweike on 17/1/3.
 */
public abstract class BaseEvent {

    private Long eventId;
    private Long timestamp;
    private EventSource eventSource;

    public BaseEvent() {
        eventSource = EventSource.ORDER_SERVER;
        timestamp = System.currentTimeMillis();
    }

    void genEventId() {
        EventFactory eventFactory = SpringUtils
                .getBean(EventFactory.class);
        eventId = eventFactory.nextEventId();
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    public abstract String routingKey();
}
