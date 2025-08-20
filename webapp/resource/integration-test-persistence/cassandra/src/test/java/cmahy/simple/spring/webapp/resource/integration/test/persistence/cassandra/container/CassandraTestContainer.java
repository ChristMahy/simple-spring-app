package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.exception.CassandraTestContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.containers.Container;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class CassandraTestContainer extends CassandraContainer {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraTestContainer.class);

    protected CassandraTestContainer() {
        super("cassandra:latest");

        this
            .withCopyToContainer(
                MountableFile.forClasspathResource("cassandra/db/migration", MountableFile.DEFAULT_DIR_MODE),
                "/tmp/taco_cloud_init_scripts"
            )
            .withCopyToContainer(
                MountableFile.forClasspathResource("snapshots/scripts", MountableFile.DEFAULT_DIR_MODE),
                "/tmp/snapshots/scripts"
            )
            .withEnv("CASSANDRA_KEYSPACE", CassandraTestContainerConstant.KEYSPACE)
            .withReuse(false);
    }

    @Override
    public void start() {
        try {
            LOG.info("Starting cassandra test container");

            super.start();

//            cassandraContainer.followOutput(new Slf4jLogConsumer(LOG));
        } catch (Exception e) {
            throw new CassandraTestContainerException("Failed to start cassandra test container", e);
        }

        try {
            LOG.info("Starting cassandra data source");

            String schemaName = CassandraTestContainerConstant.KEYSPACE;
//            String schemaName = "taco_cloud_" + UUID.randomUUID().toString().toLowerCase().replaceAll("-", "_");

            List<String[]> commands = new ArrayList<>(2) {{
                add(new String[]{
                    "/bin/sh", "-c",
                    "cp -r /tmp/taco_cloud_init_scripts /tmp/" + schemaName
                });
                add(new String[]{
                    "/bin/sh", "-c",
                    "find /tmp/" + schemaName + " -maxdepth 1 -type f | sort | xargs -I {} sed -i 's/keyspace_placeholder/" + schemaName + "/g' {}"
                });
                add(new String[]{
                    "/bin/sh", "-c",
                    "find /tmp/" + schemaName + " -maxdepth 1 -type f | sort | xargs -I {} cqlsh -f {}"
                });
            }};

            executeCommands(commands);

            Instant startTime = Instant.now().plusSeconds(120);

            String[] describeCommand = new String[]{"/bin/sh", "-c", "cqlsh -e 'describe keyspace " + schemaName + ";'"};

            Container.ExecResult describeResult = execInContainer(describeCommand);

            while (describeResult.getExitCode() != 0 && Instant.now().isBefore(startTime)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) { }

                describeResult = execInContainer(describeCommand);
            }

            if (describeResult.getExitCode() != 0) {
                throw new CassandraTestContainerException("Failed to start cassandra test container, schema not found");
            }

            LOG.info("Cassandra started at {}:{}", getHost(), getMappedPort(9042));

        } catch (RuntimeException cassandraTestContainerException) {
            throw cassandraTestContainerException;
        } catch (Exception e) {
            throw new CassandraTestContainerException("Failed to start cassandra test container", e);
        }
    }

    private void executeCommands(List<String[]> commands) {
        try {
            for (String[] command : commands) {
                Container.ExecResult commandResult = execInContainer(command);

                if (commandResult.getExitCode() != 0) {
                    throw new CassandraTestContainerException(commandResult.getStderr());
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new CassandraTestContainerException(e.getMessage(), e);
        }
    }
}
