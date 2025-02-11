package cmahy.webapp.user.adapter.jpa.repository;

import cmahy.webapp.user.adapter.jpa.entity.domain.JpaUser;
import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryImpl extends UserRepository<JpaUser>, JpaRepository<JpaUser, UUID> {

    @Override
    default Optional<JpaUser> findById(UserId id) {
        return this.findById(id.value());
    }
}
