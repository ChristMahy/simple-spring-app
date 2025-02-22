package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource.CassandraTestContainerInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

public class CassandraContextClosedEventListener implements ApplicationListener<ContextStoppedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraContextClosedEventListener.class);

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        try {
            CassandraTestContainerInstance.TEST_CONTAINER.stopImpl();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }
}
