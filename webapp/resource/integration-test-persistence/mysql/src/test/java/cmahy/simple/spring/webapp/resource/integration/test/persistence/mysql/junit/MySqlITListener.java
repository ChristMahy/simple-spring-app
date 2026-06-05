package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.junit;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.annotation.CleanupPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MySqlITListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITListener.class);

    @Override
    public void afterTestExecution(TestContext testContext) throws Exception {

        List<Annotation> annotations = Arrays.stream(testContext.getTestMethod().getAnnotations())
            .filter(annotation -> annotation instanceof CleanupPersistence)
            .toList();

        if (annotations.size() != 1) {
            return;
        }

        CleanupPersistence cleanupInstruction = (CleanupPersistence) annotations.getFirst();

        if (!cleanupInstruction.required()) {
            return;
        }

        ApplicationContext applicationContext = testContext.getApplicationContext();

        if (Objects.nonNull(applicationContext)) {

            DataSource dataSource = applicationContext.getBean(DataSource.class);

            try (Connection connection = dataSource.getConnection()) {

                try (Statement statement = connection.createStatement()) {

                    statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

                }

                try (Statement statement = connection.createStatement()) {

                    ResultSet resultSet = statement.executeQuery(
                        "SELECT TABLE_NAME, TABLE_TYPE, TABLE_SCHEMA, TABLE_CATALOG " +
                            "FROM INFORMATION_SCHEMA.TABLES " +
                            "WHERE TABLE_SCHEMA = DATABASE() " +
                            "AND TABLE_TYPE = 'BASE TABLE';"
                    );

                    Set<String> ignoredTables = Stream.concat(
                            Arrays.stream(cleanupInstruction.ignoreTables()),
                            Stream.of("DATABASECHANGELOG", "DATABASECHANGELOGLOCK")
                        )
                        .collect(Collectors.toSet());

                    while (resultSet.next()) {

                        String tableName = resultSet.getString("TABLE_NAME");

                        if (ignoredTables.contains(tableName)) {

                            continue;

                        }

                        try (Statement deleteStatement = connection.createStatement()) {

                            deleteStatement.execute("DELETE FROM `" + tableName + "` WHERE TRUE");

                        }

                    }

                }

                try (Statement statement = connection.createStatement()) {

                    statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

                }

            } catch (Exception any) {

                throw new RuntimeException(any);

            }

        }

    }
}
