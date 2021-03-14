package com.timyang.playground.intregration.deliver;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

@Configuration
public class AmqpFlowConfiguration {

    private static final String topicExchangeName = "si.topic-exchange";
    private static final String queueName = "si.test.deliver-q";

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

//    @Bean
//    public SimpleMessageListenerContainer amqpContainer(
//            ConnectionFactory connectionFactory
//            ,MessageListenerAdapter listenerAdapter
//    ) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setConcurrentConsumers(2);
//        container.setReceiveTimeout(30000);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(2);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        final MessageListenerAdapter receiveMessage = new MessageListenerAdapter(receiver, "receiveMessage");
//
//        final Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
//        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
//        mapper.setTrustedPackages("com.timyang.playground.intregration.deliver");
//        jsonConverter.setJavaTypeMapper(mapper);
//
//        receiveMessage.setMessageConverter(jsonConverter);
//        return receiveMessage;
//    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

//    @Bean
//    public Jackson2JsonMessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }

//    @Bean
//    @Transformer(inputChannel = "amqpInboundChannel", outputChannel = "inboundEventChannel")
//    JsonToObjectTransformer jsonToObjectTransformer() {
//        return new JsonToObjectTransformer(InboundEvent.class);
//    }

    /**
     * https://stackoverflow.com/questions/29592543/how-to-configure-and-receiveandconvert-json-payload-into-domain-object-in-spring/34224538
     */
    @Component
    class MyRabbitListenerConfigurer implements RabbitListenerConfigurer {
        @Override
        public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
            rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
        }
    }

}
