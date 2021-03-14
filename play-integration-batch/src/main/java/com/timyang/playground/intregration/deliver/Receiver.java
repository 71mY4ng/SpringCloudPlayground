package com.timyang.playground.intregration.deliver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    MessageChannel inboundEventChannel;

    public InboundEvent receiveMessage(InboundEvent message) {
        LOGGER.info("receive message <{}>", message);
        return message;
    }

    @RabbitListener(queues = "si.test.deliver-q")
    public void fromRabbit(@Payload InboundEvent message) {
        LOGGER.info("receive message <{}>", message);
        inboundEventChannel.send(MessageBuilder.withPayload(message).build());
    }
}
