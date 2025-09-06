package com.crudkiller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.crudkiller")
@EnableConfigurationProperties(CrudKillerProperties.class)
@ConditionalOnProperty(prefix = "crud-killer", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CrudKillerAutoConfiguration {

    public CrudKillerAutoConfiguration() {
        log.info("CRUD Killer Framework initialized successfully");
    }
}