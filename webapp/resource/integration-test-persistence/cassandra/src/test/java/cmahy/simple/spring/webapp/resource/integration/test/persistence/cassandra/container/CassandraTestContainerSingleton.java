package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.container;

import org.testcontainers.cassandra.CassandraContainer;

public enum CassandraTestContainerSingleton {

    INSTANCE;

    private final CassandraContainer container;

    CassandraTestContainerSingleton() {
        container = new CassandraTestContainer();
    }

    public CassandraContainer container() {
        return container;
    }

}
