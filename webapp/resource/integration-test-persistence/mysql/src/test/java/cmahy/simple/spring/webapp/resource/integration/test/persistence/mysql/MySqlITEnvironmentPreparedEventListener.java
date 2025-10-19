package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainer;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MySqlITEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlITEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        MySqlTestContainer mySqlInstance = MySqlTestContainerSingleton.INSTANCE.container();

        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> mySqlDataSourceOverrideProperties = new HashMap<>();

        mySqlDataSourceOverrideProperties.put("spring.datasource.url", mySqlInstance.getJdbcUrl());
        mySqlDataSourceOverrideProperties.put("spring.datasource.username", mySqlInstance.getUsername());
        mySqlDataSourceOverrideProperties.put("spring.datasource.password", mySqlInstance.getPassword());

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
