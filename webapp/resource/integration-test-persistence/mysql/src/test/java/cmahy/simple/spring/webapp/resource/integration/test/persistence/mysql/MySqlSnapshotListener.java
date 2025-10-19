package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.Objects;
import java.util.Optional;

public class MySqlSnapshotListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlSnapshotListener.class);

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

        ApplicationContext applicationContext = testContext.getApplicationContext();

        if (Objects.nonNull(applicationContext)) {

            EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);

            Optional.ofNullable(entityManagerFactory)
                .map(EntityManagerFactory::getCache)
                .ifPresent(cache -> {
                    LOG.info("Clearing entity manager caches");

                    cache.evictAll();
                });

            Object dataSource = applicationContext.getBean("dataSource");

            if (Objects.nonNull(dataSource) && dataSource instanceof HikariDataSource) {
                LOG.info("Evicting all connections in HikariCP pool");

                ((HikariDataSource) dataSource).getHikariPoolMXBean().softEvictConnections();
            }

        }

        SnapshotSingleton.SNAPSHOT.restore();

    }
}
