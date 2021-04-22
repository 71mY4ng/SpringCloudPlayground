package com.timyang.playground.intregration.deliver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeliverEventApplication.class)
public class DeliverEventTest {

    @Autowired
    public MessageChannel inboundEventChannel;

    static final long maxDateTime = LocalDateTime.MIN.atZone(ZoneId.systemDefault()).toEpochSecond();
    static final long minDateTime = LocalDateTime.MAX.atZone(ZoneId.systemDefault()).toEpochSecond();

    @Test
    public void test() {
        final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        for (int i = 0; i < 50; i++) {
            final InboundEvent inboundEvent = new InboundEvent();
            inboundEvent.setId("" + i);
            inboundEvent.setSource("dummy-source-" + inboundEvent.getId());
            inboundEvent.setBody("dummy-body");
            inboundEvent.setTime(someDayNowTime().format(FMT));

            final Message<InboundEvent> build = MessageBuilder.withPayload(inboundEvent).build();
            inboundEventChannel.send(build);
        }
    }

    private static LocalDateTime someDayNowTime() {
        final long rndEpoch = ThreadLocalRandom.current().nextLong(maxDateTime, minDateTime);

        return Instant.ofEpochSecond(rndEpoch)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
