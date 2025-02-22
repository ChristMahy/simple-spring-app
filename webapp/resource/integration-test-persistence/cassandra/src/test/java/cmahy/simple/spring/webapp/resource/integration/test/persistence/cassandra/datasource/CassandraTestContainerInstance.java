package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource.exception.CassandraTestContainerException;

import java.util.Optional;

public enum CassandraTestContainerInstance implements CassandraTestContainer {

    TEST_CONTAINER;

    private final CassandraTestContainer cassandraTestContainer;

    CassandraTestContainerInstance() {
        cassandraTestContainer = new CassandraTestContainerImpl();
    }

    @Override
    public void startImpl() throws CassandraTestContainerException {
        cassandraTestContainer.startImpl();
    }

    @Override
    public void stopImpl() throws CassandraTestContainerException {
        cassandraTestContainer.stopImpl();
    }

    @Override
    public Optional<CassandraTestContainerInfo> cassandraTestContainerInfo() {
        return cassandraTestContainer.cassandraTestContainerInfo();
    }
}
