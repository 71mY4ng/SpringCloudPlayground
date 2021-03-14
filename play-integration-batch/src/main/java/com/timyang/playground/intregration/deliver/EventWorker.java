package com.timyang.playground.intregration.deliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class EventWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventWorker.class);

    @ServiceActivator(inputChannel = "deliverChannel", outputChannel = "nodeCompletionChannel",
            adviceChain = {
                    "retryAdvice"
            }
    )
    public EventEntity postEventWorker(EventEntity event) {
        LOGGER.info("[prepare post event] try event: {}", event.getId());
        final int second = event.getTime().getSecond();

        if (second % 2 == 0) {
            throw new RuntimeException("dummy");
        } else {
            LOGGER.info("[posted] event: {}", event.getId());
            return event;
        }
    }
}
