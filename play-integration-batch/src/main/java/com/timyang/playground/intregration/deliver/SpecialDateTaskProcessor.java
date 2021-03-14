package com.timyang.playground.intregration.deliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class SpecialDateTaskProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialDateTaskProcessor.class);

    @ServiceActivator(inputChannel = "yearEndTaskChannel", outputChannel = "deliverChannel")
    public EventEntity handleYearEndTask(EventEntity event) {
        LOGGER.info("[year end] {}", event);

        return event;
    }

    @ServiceActivator(inputChannel = "monthEndTaskChannel", outputChannel = "deliverChannel")
    public EventEntity handleMonthEndTask(EventEntity event) {
        LOGGER.info("[month end] {}", event);

        return event;
    }

    @ServiceActivator(inputChannel = "weekendTaskChannel", outputChannel = "deliverChannel")
    public EventEntity handleWeekendTask(EventEntity event) {
        LOGGER.info("[weekend] {}", event);

        return event;
    }
}
