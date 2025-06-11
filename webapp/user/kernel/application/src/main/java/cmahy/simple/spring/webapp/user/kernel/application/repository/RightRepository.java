package cmahy.simple.spring.webapp.user.kernel.application.repository;

import cmahy.simple.spring.webapp.user.kernel.domain.Right;

import java.util.Optional;

public interface RightRepository<R extends Right> {

    Optional<R> findByName(String name);

    R save(R right);
    
}
