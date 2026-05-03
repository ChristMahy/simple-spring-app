package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.listener;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainer;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

import static cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerConstant.DATABASE_NAME_PROPERTY_KEY;

public class MySqlITEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        ConfigurableEnvironment environment = event.getEnvironment();

        MySqlTestContainer mySqlInstance = MySqlTestContainerSingleton.INSTANCE.container();

        String dbName = "webapp_resource_cloud_" + UUID.randomUUID().toString().replace("-", "_");

        try (
            Connection conn = DriverManager.getConnection(
                mySqlInstance.getJdbcUrl(),
                "root",
                mySqlInstance.getPassword()
            );
            Statement statement = conn.createStatement()
        ) {

            statement.execute("CREATE DATABASE " + dbName);
            statement.execute("GRANT ALL PRIVILEGES ON " + dbName + ".* TO '" + mySqlInstance.getUsername() + "'@'%'");
            statement.execute("FLUSH PRIVILEGES");

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> mySqlDataSourceOverrideProperties = new HashMap<>();

        mySqlDataSourceOverrideProperties.put(
            DATABASE_NAME_PROPERTY_KEY, dbName
        );
        mySqlDataSourceOverrideProperties.put(
            "spring.datasource.url", mySqlInstance.getJdbcUrl().replace(mySqlInstance.getDatabaseName(), dbName)
        );
        mySqlDataSourceOverrideProperties.put(
            "spring.datasource.username", mySqlInstance.getUsername()
        );
        mySqlDataSourceOverrideProperties.put(
            "spring.datasource.password", mySqlInstance.getPassword()
        );

        propertySources.addFirst(new MapPropertySource(
            "mySqlDataSourceOverrideProperties", mySqlDataSourceOverrideProperties
        ));

        Stream.of(
                "spring.datasource.url",
                "spring.datasource.username",
                "spring.datasource.password"
            )
            .forEach(propName -> {
                LOG.info("Property <{}> found ? <{}>", propName, environment.getProperty(propName));
            });

    }

    @Override
    public boolean supportsAsyncExecution() {
        return false;
    }

}
