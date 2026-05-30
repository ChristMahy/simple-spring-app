//package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.service;
//
//import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerConstant;
//import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container.CassandraTestContainerSingleton;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//import org.testcontainers.containers.Container;
//
//import java.io.IOException;
//
//@Service
//public class CassandraITKeyspaceSnapshot {
//
//    private static final Logger LOG = LoggerFactory.getLogger(CassandraITKeyspaceSnapshot.class);
//
//    private final Environment environment;
//
//    public CassandraITKeyspaceSnapshot(Environment environment) {
//        this.environment = environment;
//    }
//
//    public void createSnapshot() throws IOException, InterruptedException {
//
////        LOG.info("Creating snapshot for CassandraTestContainer");
////
////        String keyspace = environment.getRequiredProperty(CassandraTestContainerConstant.KEYSPACE_PROPERTY_KEY);
////
////        execInContainer("bash", "/tmp/snapshots/scripts/create-snapshots.sh", keyspace);
//
//    }
//
//    public void restoreSnapshot() throws IOException, InterruptedException {
//
////        LOG.info("Restoring snapshot for CassandraTestContainer");
////
////        String keyspace = environment.getRequiredProperty(CassandraTestContainerConstant.KEYSPACE_PROPERTY_KEY);
////
////        execInContainer("bash", "/tmp/snapshots/scripts/restore-snapshots.sh", keyspace);
//
//    }
//
//    private void execInContainer(String... command) throws IOException, InterruptedException {
//
//        Container.ExecResult result = CassandraTestContainerSingleton
//            .INSTANCE
//            .container()
//            .execInContainer(command);
//
//        if (result.getExitCode() != 0) {
//            throw new RuntimeException(String.format(
//                "Command failed: %s\nStdout: %s\nStderr: %s",
//                String.join(" ", command),
//                result.getStdout(),
//                result.getStderr()
//            ));
//        }
//
//    }
//}
