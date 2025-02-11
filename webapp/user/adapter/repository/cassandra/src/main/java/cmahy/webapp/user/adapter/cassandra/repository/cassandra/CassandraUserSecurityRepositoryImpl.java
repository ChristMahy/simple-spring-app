package cmahy.webapp.user.adapter.cassandra.repository.cassandra;

import cmahy.webapp.user.adapter.cassandra.entity.domain.CassandraUserSecurityImpl;
import cmahy.webapp.user.kernel.domain.AuthProvider;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CassandraUserSecurityRepositoryImpl extends CassandraRepository<CassandraUserSecurityImpl, UUID> {

    @Query("select * from user where userName = ?0 and authProvider = ?1 and discriminator = '1' allow filtering")
    Optional<CassandraUserSecurityImpl> findByUserNameAndAuthProvider(String username, String authProvider);

    default Optional<CassandraUserSecurityImpl> findByUserNameAndAuthProvider(String username, AuthProvider authProvider) {
        return findByUserNameAndAuthProvider(username, authProvider.name());
    }

    @Query("select * from user where id = ?0 and discriminator = '1' allow filtering")
    Optional<CassandraUserSecurityImpl> findById(UUID userId);
}
