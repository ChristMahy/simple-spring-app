package cmahy.springapp.resourceserver.repository;

import cmahy.springapp.resourceserver.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Long> {
    @Query("select u from User u where u.username = :email")
    Optional<User> findByEmail(String email);
}
