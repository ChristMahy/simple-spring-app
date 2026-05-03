package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.listener;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerConstant;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerSingleton;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.exception.CassandraTestContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.*;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.containers.Container;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class CassandraITEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraITEnvironmentPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        LOG.info("CassandraITEnvironmentPreparedEventListener onApplicationEvent");

        ConfigurableEnvironment environment = event.getEnvironment();

        CassandraContainer cassandraInstance = CassandraTestContainerSingleton.INSTANCE.container();

        String keyspaceName = "taco_cloud_" + UUID.randomUUID().toString().replace("-", "_");

        List<String[]> commands = new ArrayList<>(2) {{
            add(new String[]{
                "/bin/sh", "-c",
                "cp -r /tmp/taco_cloud_init_scripts /tmp/" + keyspaceName
            });
            add(new String[]{
                "/bin/sh", "-c",
                "find /tmp/" + keyspaceName + " -maxdepth 1 -type f | sort | xargs -I {} sed -i 's/keyspace_placeholder/" + keyspaceName + "/g' {}"
            });
            add(new String[]{
                "/bin/sh", "-c",
                "find /tmp/" + keyspaceName + " -maxdepth 1 -type f | sort | xargs -I {} cqlsh -f {}"
            });
        }};

        executeCommands(cassandraInstance, commands);

        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> cassandraDataSourceOverrideProperties = new HashMap<>();

        cassandraDataSourceOverrideProperties.put(
            CassandraTestContainerConstant.KEYSPACE_PROPERTY_KEY, keyspaceName
        );
        cassandraDataSourceOverrideProperties.put(
            "spring.cassandra.local-datacenter", cassandraInstance.getLocalDatacenter()
        );
        cassandraDataSourceOverrideProperties.put(
            "spring.cassandra.keyspace-name", keyspaceName
        );
        cassandraDataSourceOverrideProperties.put(
            "spring.cassandra.contact-points", cassandraInstance.getHost()
        );
        cassandraDataSourceOverrideProperties.put(
            "spring.cassandra.port", cassandraInstance.getMappedPort(9042)
        );

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

    private void executeCommands(CassandraContainer cassandraInstance, List<String[]> commands) {
        try {
            for (String[] command : commands) {
                Container.ExecResult commandResult = cassandraInstance.execInContainer(command);

                if (commandResult.getExitCode() != 0) {
                    throw new CassandraTestContainerException(commandResult.getStderr());
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new CassandraTestContainerException(e.getMessage(), e);
        }
    }

}
