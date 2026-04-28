package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.time.Duration;
import java.util.*;

public class H2ITDatasourceSnapshotListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(H2ITDatasourceSnapshotListener.class);

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

        ApplicationContext applicationContext = testContext.getApplicationContext();

        if (Objects.nonNull(applicationContext)) {

            H2ITDatasourceSnapshot datasourceSnapshot = applicationContext.getBean(H2ITDatasourceSnapshot.class);

            datasourceSnapshot.restore();
//
//            EntityManagerFactory entityManagerFactory = applicationContext.getBean(EntityManagerFactory.class);
//
//            Optional.ofNullable(entityManagerFactory)
//                .map(EntityManagerFactory::getCache)
//                .ifPresent(cache -> {
//                    LOG.info("Clearing entity manager caches");
//
//                    cache.evictAll();
//                });
//
//            try {
//
//                HikariDataSource dataSource = applicationContext.getBean(HikariDataSource.class);
//
//                LOG.info(
//                    "Evicting all connections in HikariCP pool, active <{}>, idle <{}>, total <{}>",
//                    dataSource.getHikariPoolMXBean().getActiveConnections(),
//                    dataSource.getHikariPoolMXBean().getIdleConnections(),
//                    dataSource.getHikariPoolMXBean().getTotalConnections()
//                );
//
//                dataSource.getHikariPoolMXBean().softEvictConnections();
//
//            } catch (BeansException ignored) {}
//
//        }
//
//        new Thread(() -> SnapshotSingleton.SNAPSHOT.restore(applicationContext)).start();
//
//        int retryCountdown = 100;
//
//        Duration sleepDuration = Duration.ofMillis(300);
//        SnapshotSingleton.DatasourceInfo datasource = SnapshotSingleton.SNAPSHOT.datasourceInfo();
//
//        while (retryCountdown > 0) {
//
//            try (Connection connection = DriverManager.getConnection(datasource.url(), datasource.username(), datasource.password())) {
//
//                if (LOG.isDebugEnabled()) {
//
//                    try (
//                        Statement statement = connection.createStatement();
//                        ResultSet resultSet = statement.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'")
//                    ) {
//
//                        LOG.debug("Showing tables <{}> ----------------------------------------------------------", retryCountdown);
//                        Set<String> tables = new HashSet<>();
//
//                        while (resultSet.next()) {
//
//                            tables.add(resultSet.getString("TABLE_NAME"));
//
//                        }
//
//                        LOG.debug("Tables <{}>", String.join(", ", tables));
//
//                    }
//
//                    try (
//                        Statement statement = connection.createStatement();
//                        ResultSet resultSet = statement.executeQuery(
//                            "SELECT * " +
//                            "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS " +
//                            "WHERE TABLE_NAME = 'USER_APP'"
//                        )
//                    ) {
//
//                        LOG.debug("Showing constraints <{}> ----------------------------------------------------------", retryCountdown);
//
//                        ResultSetMetaData metaData = resultSet.getMetaData();
//                        int columnCount = metaData.getColumnCount();
//
//                        Collection<String> columns = new ArrayList<>(columnCount);
//
//                        for (int i = 1; i <= columnCount; i++) {
//
//                            columns.add("(" + i + ") " + metaData.getColumnName(i));
//
//                        }
//
//                        LOG.debug("Columns <{}>", String.join(", ", columns));
//
//                        while (resultSet.next()) {
//
//                            Collection<String> values = new ArrayList<>(columnCount);
//
//                            for (int i = 1; i <= columnCount; i++) {
//
//                                values.add("(" + i + ") " + resultSet.getString(i));
//
//                            }
//
//                            LOG.debug("Row <{}>", String.join(", ", values));
//
//                        }
//
//                    }
//
//                }
//
//                try (
//                    Statement statement = connection.createStatement();
//                    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'")
//                ) {
//
//                    if (resultSet.next() && resultSet.getInt(1) == datasource.tablesCount()) {
//
//                        retryCountdown = Integer.MIN_VALUE;
//
//                    } else {
//
//                        validateCountdownAndSleepALittleTime(retryCountdown--, sleepDuration);
//
//                    }
//
//                }
//
//
//
//            } catch (Exception any) {
//
//                LOG.debug("Exception while health check", any);
//
//                validateCountdownAndSleepALittleTime(retryCountdown--, sleepDuration);
//
//            }

        }

    }

    private void validateCountdownAndSleepALittleTime(int retryCountdown, Duration sleepDuration) {

        LOG.info("Retry count down <{}>", retryCountdown);

        if (retryCountdown <= 0) {

            throw new IllegalStateException("Database isn't ready");

        }

        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {}

    }

}
