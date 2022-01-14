package com.gorent.api;

import com.gorent.api.config.GorentProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        GorentProperties.class
})
public class GorentBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GorentBackendApplication.class, args);
    }

}
