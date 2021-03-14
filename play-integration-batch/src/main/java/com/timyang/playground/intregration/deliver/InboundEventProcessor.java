package com.timyang.playground.intregration.deliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class InboundEventProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeCompletionService.class);

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public EventEntity process(@Payload InboundEvent inbound) {
        LOGGER.info("[inbound] >>> {}", inbound);

        final LocalDateTime eventTime = LocalDateTime.parse(inbound.getTime(), FMT);

        return parseEvent(inbound, eventTime);
    }

    private EventEntity parseEvent(InboundEvent inbound, LocalDateTime eventTime) {
        EventEntity event = new EventEntity();
        event.setId(Integer.parseInt(inbound.getId()));
        event.setBody(inbound.getBody());
        event.setTime(eventTime);
        event.setTaskType(parseSpecialDateType(eventTime));

        return event;
    }

    private String parseSpecialDateType(LocalDateTime eventTime) {
        if (eventTime.with(TemporalAdjusters.lastDayOfMonth()).isEqual(eventTime)) {
            return "Month_End";
        } else if (eventTime.with(TemporalAdjusters.lastDayOfYear()).isEqual(eventTime)) {
            return "Year_End";
        } else if (eventTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || eventTime.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {

            return "Weekend";
        } else {
            return "Plain";
        }
    }
}
