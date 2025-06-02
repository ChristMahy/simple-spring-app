package cmahy.simple.spring.webapp.authorization.application.repository;

import cmahy.simple.spring.webapp.authorization.domain.Right;

import java.util.Optional;

public interface RightRepository {

    Right save(Right right);

    Optional<Right> findByName(String name);

}
