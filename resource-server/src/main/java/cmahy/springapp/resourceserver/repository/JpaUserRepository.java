package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {
}
