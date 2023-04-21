package cmahy.springapp.jms.client.scheduler;


import cmahy.springapp.jms.client.service.JmsService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableScheduling
public class JmsSchedulerQueue {
    private static final Logger LOG = getLogger(JmsSchedulerQueue.class);

    private final JmsService jmsService;

    public JmsSchedulerQueue(JmsService jmsService) {
        this.jmsService = jmsService;
    }

    @Scheduled(initialDelay = 2, fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void pullMessage() {
        LOG.info("Pull request to messages queue");

        jmsService.receiveMessage();
    }
}
