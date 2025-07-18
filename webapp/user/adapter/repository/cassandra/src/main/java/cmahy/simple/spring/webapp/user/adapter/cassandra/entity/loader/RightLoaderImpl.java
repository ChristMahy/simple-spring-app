package cmahy.simple.spring.webapp.user.adapter.cassandra.entity.loader;

import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRight;
import cmahy.simple.spring.webapp.user.adapter.cassandra.entity.domain.CassandraRole;
import cmahy.simple.spring.webapp.user.adapter.cassandra.repository.cassandra.CassandraRoleRepositoryImpl;
import cmahy.simple.spring.webapp.user.kernel.domain.id.RightId;
import jakarta.inject.Named;

import java.util.Collection;

@Named
public class RightLoaderImpl implements RightLoader {

    private final CassandraRoleRepositoryImpl cassandraRoleRepository;

    public RightLoaderImpl(
        CassandraRoleRepositoryImpl cassandraRoleRepository
    ) {
        this.cassandraRoleRepository = cassandraRoleRepository;
    }

    @Override
    public Class<CassandraRight> entityClass() {
        return CassandraRight.class;
    }

    @Override
    public Collection<CassandraRole> loadRoles(RightId rightId) {
        return cassandraRoleRepository.findAllByRightId(rightId);
    }

}
