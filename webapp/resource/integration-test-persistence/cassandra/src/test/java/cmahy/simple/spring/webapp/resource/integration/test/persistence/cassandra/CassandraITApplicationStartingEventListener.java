package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class CassandraITApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraITApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        LOG.info("CassandraITApplicationStartingEventListener onApplicationEvent");

        try {
            CassandraTestContainerSingleton.INSTANCE.container().start();
        } catch (Exception anyExce) {
            LOG.error(anyExce.getMessage(), anyExce);

            throw new RuntimeException(anyExce);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return false;
    }
}
