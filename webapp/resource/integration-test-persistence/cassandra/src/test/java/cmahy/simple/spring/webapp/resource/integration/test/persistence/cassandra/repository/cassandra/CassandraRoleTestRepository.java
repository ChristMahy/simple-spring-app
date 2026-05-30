package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.repository.cassandra;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CassandraRoleTestRepository extends CassandraRepository<CassandraRole, UUID> {
}
