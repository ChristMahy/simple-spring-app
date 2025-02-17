package cmahy.simple.spring.brokers.consumer.core.adapter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerApplicationIntegrationTest.class);

    @Test
    void contextLoad() {
        LOG.info("Spring boot context is successfully loaded");
    }
}