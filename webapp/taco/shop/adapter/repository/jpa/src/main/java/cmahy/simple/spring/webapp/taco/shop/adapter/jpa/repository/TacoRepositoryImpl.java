package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.repository;

import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Primary
@Repository
public interface TacoRepositoryImpl extends TacoRepository<JpaTaco>, JpaRepository<JpaTaco, UUID> {

    @Override
    @Query("select t from JpaTaco t join t.ingredients i on i.id = :#{#ingredientId.value()} ")
    Set<JpaTaco> findByIngredientId(IngredientId ingredientId);
}
