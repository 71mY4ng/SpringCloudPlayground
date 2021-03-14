package com.timyang.playground.intregration.filedigest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@SpringBootApplication
public class FileProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProcessorApplication.class, args);
    }

    @Bean
    public IntegrationFlow processFileFlow() {
        return IntegrationFlows.from("inputFileChannel")
                .transform(fileTransformJob())
                .handle("fileProcessor", "process").get();
    }

    @Bean
    public MessageChannel inputFileChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "inputFileChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageResource() {
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilters(
                new SimplePatternFileListFilter("hk_sparc_4003_*_ver*.csv.complete"),
                new LastModifiedFileFilter()
        );

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        source.setDirectory(new File("/tmp/integration-batch/file-processor/"));
        source.setFilter(filters);

        return source;
    }

    @Bean
    public FileToStringTransformer fileTransformJob() {
        return new FileToStringTransformer();
    }

    @Bean
    public FileProcessor fileProcessor() {
        return new FileProcessor();
    }
}
