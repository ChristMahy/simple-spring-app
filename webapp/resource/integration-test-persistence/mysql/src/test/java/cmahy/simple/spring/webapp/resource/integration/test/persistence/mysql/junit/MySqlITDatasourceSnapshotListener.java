package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.junit;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.service.MySqlITDatasourceSnapshot;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.Objects;
import java.util.Optional;

public class MySqlITDatasourceSnapshotListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITDatasourceSnapshotListener.class);

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

            try {

                HikariDataSource dataSource = applicationContext.getBean(HikariDataSource.class);

                LOG.info(
                    "Evicting all connections in HikariCP pool, active <{}>, idle <{}>, total <{}>",
                    dataSource.getHikariPoolMXBean().getActiveConnections(),
                    dataSource.getHikariPoolMXBean().getIdleConnections(),
                    dataSource.getHikariPoolMXBean().getTotalConnections()
                );

                dataSource.getHikariPoolMXBean().softEvictConnections();

            } catch (BeansException ignored) {}

        }

        MySqlITDatasourceSnapshot datasourceSnapshot = applicationContext.getBean(MySqlITDatasourceSnapshot.class);

        datasourceSnapshot.restore();

    }
}
