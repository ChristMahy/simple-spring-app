package cmahy.webapp.user.adapter.jpa.repository;

import cmahy.webapp.user.kernel.application.repository.UserRepository;
import cmahy.webapp.user.kernel.domain.User;
import cmahy.webapp.user.kernel.domain.id.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends UserRepository, JpaRepository<User, Long> {

    @Override
    default Optional<User> findById(UserId id) {
        return this.findById(id.value());
    }
}
