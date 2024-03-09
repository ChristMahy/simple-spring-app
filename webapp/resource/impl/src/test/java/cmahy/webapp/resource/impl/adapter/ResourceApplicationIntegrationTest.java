package cmahy.webapp.resource.impl.adapter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceApplicationIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceApplicationIntegrationTest.class);

    @Test
    void contextLoad() {
        LOG.info("Spring boot context is successfully loaded");
    }
}