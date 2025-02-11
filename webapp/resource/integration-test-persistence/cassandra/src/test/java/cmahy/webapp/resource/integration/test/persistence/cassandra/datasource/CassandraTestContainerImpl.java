package cmahy.webapp.resource.integration.test.persistence.cassandra.datasource;

import cmahy.webapp.resource.integration.test.persistence.cassandra.datasource.exception.CassandraTestContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class CassandraTestContainerImpl extends CassandraContainer<CassandraTestContainerImpl> implements CassandraTestContainer {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraTestContainerImpl.class);

    private Optional<CassandraTestContainerInfo> testContainerInfo = Optional.empty();

    public CassandraTestContainerImpl() {
        super("cassandra:latest");

        this
            .withCopyToContainer(MountableFile
                .forClasspathResource("cassandra/db/migration", MountableFile.DEFAULT_DIR_MODE),
            "/tmp/taco_cloud_init_scripts"
            )
            .withExposedPorts(CassandraContainer.CQL_PORT)
            .waitingFor(Wait.forSuccessfulCommand("cqlsh -e 'SELECT release_version FROM system.local'"))
            .withStartupTimeout(Duration.ofMinutes(2));
    }

    @Override
    public void startImpl() throws CassandraTestContainerException {
        try {
            LOG.info("Starting cassandra test container");

            super.start();

//            cassandraContainer.followOutput(new Slf4jLogConsumer(LOG));
        } catch (Exception e) {
            throw new CassandraTestContainerException("Failed to start cassandra test container", e);
        }

        try {
            LOG.info("Starting cassandra data source");

            String schemaName = "taco_cloud_" + UUID.randomUUID().toString().toLowerCase().replaceAll("-", "_");

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

            LOG.info("Cassandra started at {}:{}", getHost(), getMappedPort(CassandraContainer.CQL_PORT));

            testContainerInfo = Optional.of(new CassandraTestContainerInfo(
                Optional.ofNullable(getHost()),
                Optional.ofNullable(getLocalDatacenter()),
                Optional.of(schemaName),
                Optional.ofNullable(getMappedPort(CassandraContainer.CQL_PORT))
            ));
        } catch (CassandraTestContainerException cassandraTestContainerException) {
            throw cassandraTestContainerException;
        } catch (Exception e) {
            throw new CassandraTestContainerException("Failed to start cassandra test container", e);
        }
    }

    @Override
    public Optional<CassandraTestContainerInfo> cassandraTestContainerInfo() {
        return testContainerInfo;
    }

    //    @Override
//    public void removeDataSource(CassandraTestContainerInfo dataSourceInfo) throws CassandraTestContainerException {
//        LOG.info("Drop cassandra data source <{}>", dataSourceInfo);
//
//        List<String[]> commands = new ArrayList<>(2) {{
//            add(new String[]{
//                "/bin/sh", "-c",
//                "drop keyspace " + dataSourceInfo.keyspace().orElseThrow(() -> new CassandraTestContainerException("Keyspace undefined"))
//            });
//        }};
//
//        executeCommands(commands);
//    }

    @Override
    public void stopImpl() throws CassandraTestContainerException {
        LOG.info("Stop cassandra test container");

        testContainerInfo = Optional.empty();

        stop();
    }

    private void executeCommands(List<String[]> commands) throws CassandraTestContainerException {
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
