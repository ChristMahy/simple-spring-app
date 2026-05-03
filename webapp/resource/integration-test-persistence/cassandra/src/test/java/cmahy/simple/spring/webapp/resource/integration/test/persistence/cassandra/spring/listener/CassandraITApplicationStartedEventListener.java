package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.listener;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.service.CassandraITKeyspaceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE)
public class CassandraITApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraITApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            ApplicationContext context = event.getApplicationContext();

            CassandraITKeyspaceSnapshot snapshot = context.getBean(CassandraITKeyspaceSnapshot.class);

            LOG.info("Starting keyspace snapshot with <{}>", snapshot);

            snapshot.createSnapshot();

        } catch (Exception any) {

            throw new RuntimeException(any);

        }

    }

}
