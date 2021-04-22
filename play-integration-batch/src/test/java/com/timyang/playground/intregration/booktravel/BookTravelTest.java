package com.timyang.playground.intregration.booktravel;

import net.logstash.logback.encoder.org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.integration.test.matcher.HeaderMatcher.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookFlightFlowApplication.class)
public class BookTravelTest {

    @Autowired
    MessageChannel inputChannel;

    @Autowired
    PollableChannel outputChannel;

    @Autowired
    public ExecutorAspect myAspect;

    @Before
    public void setUP() {
        myAspect.setAfterExecution(name -> {
            System.out.println(">>>> " + ReflectionToStringBuilder.toString(name));
            return null;
        });
    }

    @Test
    public void testBookFlight() {
        BookFlightCommand command = new BookFlightCommand("SFO", "ORD");

        final Message<BookFlightCommand> msg = MessageBuilder.withPayload(command)
                .setCorrelationId("ABC")
                .build();

        inputChannel.send(msg);

        final Message<?> receive = outputChannel.receive();

        assertThat(receive, hasHeaderKey("command"));
        assertThat(receive, hasHeader("command", notNullValue()));
        assertThat(receive, hasHeader("command", command));
        assertThat(receive, hasHeader("command", isA(BookFlightCommand.class)));
        assertThat(receive, hasCorrelationId("ABC"));

        Map<String, Object> map = new HashMap<>();
        map.put("command", command);
        map.put(IntegrationMessageHeaderAccessor.CORRELATION_ID, "ABC");

        assertThat(receive, hasAllHeaders(map));

    }
}
