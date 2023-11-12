package cmahy.brokers.consumer.core.adapter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppAsyncConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(AppAsyncConfigurer.class);

    @Bean
    public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
        LOG.debug("Creating Task Executor");

        return builder
            .corePoolSize(5)
            .maxPoolSize(10)
            .queueCapacity(25)
            .threadNamePrefix("cmahy-broker-consumer-")
            .build();
    }
}
