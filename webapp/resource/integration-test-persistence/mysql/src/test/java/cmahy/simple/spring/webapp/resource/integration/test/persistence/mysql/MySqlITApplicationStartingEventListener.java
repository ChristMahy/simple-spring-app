package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class MySqlITApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        LOG.info("{} onApplicationEvent", MySqlITApplicationStartingEventListener.class.getSimpleName());

        try {
            MySqlTestContainerSingleton.INSTANCE.container().start();
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
