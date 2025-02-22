package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource.CassandraTestContainerInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CassandraEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();

        CassandraTestContainerInstance.TEST_CONTAINER.cassandraTestContainerInfo().ifPresent(infos -> {
            MutablePropertySources propertySources = environment.getPropertySources();

            Map<String, Object> cassandraDataSourceOverrideProperties = new HashMap<>();

            infos.localDataCenter().ifPresent(localDatacenter -> {
                cassandraDataSourceOverrideProperties.put("spring.cassandra.local-datacenter", localDatacenter);
            });

            infos.keyspace().ifPresent(keyspace -> {
                cassandraDataSourceOverrideProperties.put("spring.cassandra.keyspace-name", keyspace);
            });

            infos.contactPoints().ifPresent(contactPoints -> {
                cassandraDataSourceOverrideProperties.put("spring.cassandra.contact-points", contactPoints);
            });
            infos.port().ifPresent(port -> {
                cassandraDataSourceOverrideProperties.put("spring.cassandra.port", port);
            });

            propertySources.addFirst(new MapPropertySource(
                "cassandraDataSourceOverrideProperties",
                cassandraDataSourceOverrideProperties
            ));
        });

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
}
