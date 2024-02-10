package cmahy.brokers.consumer.core.adapter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.time.Duration;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@EnableScheduling
public class AppAsyncConfigurer implements AsyncConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(AppAsyncConfigurer.class);

    private final TaskExecutionProperties taskExecutionProperties;
    private final TaskExecutorBuilder taskExecutorBuilder;

    public AppAsyncConfigurer(
        TaskExecutionProperties taskExecutionProperties,
        TaskExecutorBuilder taskExecutorBuilder
    ) {
        this.taskExecutionProperties = taskExecutionProperties;
        this.taskExecutorBuilder = taskExecutorBuilder;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Override
    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {
        LOG.debug("Creating Task Executor");

        return taskExecutorBuilder
            .corePoolSize(taskExecutionProperties.getPool().getCoreSize())
            .maxPoolSize(taskExecutionProperties.getPool().getMaxSize())
            .queueCapacity(taskExecutionProperties.getPool().getQueueCapacity())
            .threadNamePrefix(taskExecutionProperties.getThreadNamePrefix())
            .build();
    }

    @Bean
    protected ConcurrentTaskExecutor getTaskExecutor(Executor taskExecutor) {
        return new ConcurrentTaskExecutor(taskExecutor);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(ConcurrentTaskExecutor concurrentTaskExecutor) {
        return new WebMvcConfigurer() {
            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer
                    .setDefaultTimeout(1000 * 60 * 60)
                    .setTaskExecutor(concurrentTaskExecutor);

            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                    .addMapping("/api/v1/**")
                    .allowedOrigins("http://localhost:4200");
            }
        };
    }
}
