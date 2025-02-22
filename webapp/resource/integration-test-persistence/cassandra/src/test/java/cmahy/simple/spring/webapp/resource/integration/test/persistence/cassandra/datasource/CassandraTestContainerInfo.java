package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.datasource;

import java.util.Optional;

public record CassandraTestContainerInfo(
    Optional<String> contactPoints,
    Optional<String> localDataCenter,
    Optional<String> keyspace,
    Optional<Integer> port
) {
}
