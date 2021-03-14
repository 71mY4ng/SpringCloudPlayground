package com.timyang.playground.intregration.deliver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class NodeCompletionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeCompletionService.class);

    @ServiceActivator(inputChannel = "nodeCompletionChannel")
    public void processComplete(EventEntity completion) {
        LOGGER.info("[success] {}-{}-{}", completion.getId(), completion.getTime(), completion.getBody());
    }

}
