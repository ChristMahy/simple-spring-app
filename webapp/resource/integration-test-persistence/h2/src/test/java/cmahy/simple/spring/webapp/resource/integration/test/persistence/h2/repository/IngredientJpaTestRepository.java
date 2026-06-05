package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.repository;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.application.repository.IngredientTestRepository;
import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientJpaTestRepository extends
    JpaRepository<JpaIngredient, UUID>,
    IngredientTestRepository<JpaIngredient> {
}
