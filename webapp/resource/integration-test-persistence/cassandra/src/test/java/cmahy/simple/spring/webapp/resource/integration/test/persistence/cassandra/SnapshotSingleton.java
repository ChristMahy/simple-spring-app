package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerConstant;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Container;

import java.io.IOException;

enum SnapshotSingleton {

    SNAPSHOT;

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotSingleton.class);

    private static final String KEYSPACE = CassandraTestContainerConstant.KEYSPACE;
    private static final String NAME = "test_keyspace_cassandra_snapshot_initial";

    public void createSnapshot() throws IOException, InterruptedException {
        LOG.info("Creating snapshot for CassandraTestContainer");

        execInContainer("bash", "/tmp/snapshots/scripts/create-snapshots.sh", KEYSPACE, NAME);
    }

    public void restoreSnapshot() throws IOException, InterruptedException {
        LOG.info("Restoring snapshot for CassandraTestContainer");

        execInContainer("bash", "/tmp/snapshots/scripts/restore-snapshots.sh", KEYSPACE);
    }

    private void execInContainer(String... command) throws IOException, InterruptedException {
        Container.ExecResult result = CassandraTestContainerSingleton
            .INSTANCE
            .container()
            .execInContainer(command);

        if (result.getExitCode() != 0) {
            throw new RuntimeException(String.format(
                "Command failed: %s\nStdout: %s\nStderr: %s",
                String.join(" ", command),
                result.getStdout(),
                result.getStderr()
            ));
        }
    }
}
