package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource.exception.CassandraTestContainerException;

import java.util.Optional;

public interface CassandraTestContainer {

    void startImpl() throws CassandraTestContainerException;

    void stopImpl() throws CassandraTestContainerException;

    Optional<CassandraTestContainerInfo> cassandraTestContainerInfo();
}
