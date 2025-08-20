package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerConstant;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;
import org.testcontainers.cassandra.CassandraContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CassandraITEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraITEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        CassandraContainer cassandraInstance = CassandraTestContainerSingleton.INSTANCE.container();

        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> cassandraDataSourceOverrideProperties = new HashMap<>();

        cassandraDataSourceOverrideProperties.put("spring.cassandra.local-datacenter", cassandraInstance.getLocalDatacenter());
        cassandraDataSourceOverrideProperties.put("spring.cassandra.keyspace-name", CassandraTestContainerConstant.KEYSPACE);
        cassandraDataSourceOverrideProperties.put("spring.cassandra.contact-points", cassandraInstance.getHost());
        cassandraDataSourceOverrideProperties.put("spring.cassandra.port", cassandraInstance.getMappedPort(9042));

        propertySources.addFirst(new MapPropertySource(
            "cassandraDataSourceOverrideProperties", cassandraDataSourceOverrideProperties
        ));

        Stream.of(
                "spring.cassandra.contact-points",
                "spring.cassandra.port",
                "spring.cassandra.keyspace-name",
                "spring.cassandra.local-datacenter"
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
