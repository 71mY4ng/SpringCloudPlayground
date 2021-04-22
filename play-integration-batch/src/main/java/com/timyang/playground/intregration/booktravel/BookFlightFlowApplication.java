package com.timyang.playground.intregration.booktravel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import static org.springframework.integration.IntegrationMessageHeaderAccessor.CORRELATION_ID;

@SpringBootApplication
@ImportResource("classpath:aop-aspect.xml")
public class BookFlightFlowApplication {
    public static void main(String[] args) {

        final ConfigurableApplicationContext run = SpringApplication.run(BookFlightFlowApplication.class, args);
    }

    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    PollableChannel outputChannel() {
        return new QueueChannel();
    }

    @Autowired
    ConcreteExecutor concreteExecutor;

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public Message<?> bookFlightService(Message<BookFlightCommand> input) {
        final BookFlightCommand command = input.getPayload();

        concreteExecutor.execute(command.toString());

        return MessageBuilder.withPayload("hellow")
                .setHeader("command", command)
                .setCorrelationId(input.getHeaders().get(CORRELATION_ID))
                .build();
    }
}
