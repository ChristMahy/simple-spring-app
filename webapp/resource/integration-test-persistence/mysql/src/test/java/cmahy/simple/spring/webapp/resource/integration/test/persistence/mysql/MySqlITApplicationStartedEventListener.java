package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE)
public class MySqlITApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {
            SnapshotSingleton.SNAPSHOT.create();
        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

}
