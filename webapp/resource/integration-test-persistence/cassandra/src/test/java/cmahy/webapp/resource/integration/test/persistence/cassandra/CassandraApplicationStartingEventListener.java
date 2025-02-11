package cmahy.webapp.resource.integration.test.persistence.cassandra;

import cmahy.webapp.resource.integration.test.persistence.cassandra.datasource.CassandraTestContainerInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class CassandraApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        try {
            CassandraTestContainerInstance.TEST_CONTAINER.startImpl();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }
}
