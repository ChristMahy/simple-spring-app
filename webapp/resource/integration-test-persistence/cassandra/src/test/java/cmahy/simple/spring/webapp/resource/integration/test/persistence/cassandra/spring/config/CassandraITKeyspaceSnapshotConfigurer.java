package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.config;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.service.CassandraITKeyspaceSnapshot;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(CassandraITKeyspaceSnapshot.class)
public class CassandraITKeyspaceSnapshotConfigurer {
}
