package com.timyang.playground.intregration.deliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.handler.advice.ErrorMessageSendingRecoverer;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.messaging.MessageChannel;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@ImportResource("classpath:deliver-flow.xml")
@SpringBootApplication
public class DeliverEventApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(DeliverEventApplication.class, args);
    }

    @Bean
    public ThreadPoolTaskExecutor deliverEventThreadPoolExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setDaemon(false);
        executor.setThreadNamePrefix("deliver-event-th");
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor inboundThreadPoolExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setDaemon(false);
        executor.setThreadNamePrefix("inbound-th");
        return executor;
    }

    @Bean
    RecoveryCallback<Object> retryCallback(
            MessageChannel deliverFailChannel
    ) {
        return new ErrorMessageSendingRecoverer(deliverFailChannel);
    }

    @Bean
    RetryTemplate retryTemplate() {
        final ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(25000);

        return new RetryTemplateBuilder()
                .customPolicy(new SimpleRetryPolicy(3))
                .customBackoff(backOffPolicy)
                .build();
    }

    @Bean
    RequestHandlerRetryAdvice retryAdvice(
            RecoveryCallback<Object> retryCallback,
            RetryTemplate retryTemplate
    ) {
        final RequestHandlerRetryAdvice ad = new RequestHandlerRetryAdvice();
        ad.setRecoveryCallback(retryCallback);
        ad.setRetryTemplate(retryTemplate);
        return ad;
    }

}
