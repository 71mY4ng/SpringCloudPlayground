package com.timyang.playground.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class PlayPublicApiApplication {

    private static final Logger log = LoggerFactory.getLogger(PlayPublicApiApplication.class);

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(PlayPublicApiApplication.class);

        Environment env = app.run(args).getEnvironment();
        String protocol = "http";

        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Context-path:\t{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol, env.getProperty("server.port"),
                protocol, InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"),
                env.getActiveProfiles());

    }
}