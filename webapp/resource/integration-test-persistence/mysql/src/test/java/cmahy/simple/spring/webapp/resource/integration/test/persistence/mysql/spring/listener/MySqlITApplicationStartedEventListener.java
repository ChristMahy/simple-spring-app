package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.listener;

//import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.service.MySqlITDatasourceSnapshot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.LOWEST_PRECEDENCE)
public class MySqlITApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

//        try {
//
//            ApplicationContext context = event.getApplicationContext();
//
//            MySqlITDatasourceSnapshot snapshot = context.getBean(MySqlITDatasourceSnapshot.class);
//
//            LOG.info("Starting datasource snapshot with <{}>", snapshot);
//
//            snapshot.create();
//
//        } catch (Exception any) {
//
//            throw new RuntimeException(any);
//
//        }

    }

}
