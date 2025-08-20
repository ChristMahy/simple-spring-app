package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE)
public class H2ITApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(H2ITApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();

        SnapshotSingleton.SNAPSHOT.create(applicationContext);

    }

}
