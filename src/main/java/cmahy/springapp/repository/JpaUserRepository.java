package cmahy.springapp.repository;

import cmahy.springapp.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {
}
