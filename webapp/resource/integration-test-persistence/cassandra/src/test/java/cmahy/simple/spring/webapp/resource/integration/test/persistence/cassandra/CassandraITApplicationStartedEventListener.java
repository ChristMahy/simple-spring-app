package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class CassandraITApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            SnapshotSingleton.SNAPSHOT.createSnapshot();
        } catch (Exception any) {
            throw new RuntimeException(any);
        }
    }

}
