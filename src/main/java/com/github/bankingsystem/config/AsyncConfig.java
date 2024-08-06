package com.github.bankingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This configuration class provides a single ExecutorService implementation to the whole application and can be autowired anywhere.
 * The {@link ExecutorService#shutdown()} is called automatically to shut down any remaining threads by spring since we are telling it to call it using {@link Bean#destroyMethod()}
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

}
