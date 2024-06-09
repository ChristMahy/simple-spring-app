package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.webapp.resource.impl.application.taco.shop.repository.TacoRepository;
import cmahy.webapp.resource.impl.domain.taco.Taco;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TacoRepositoryImpl extends TacoRepository, JpaRepository<Taco, Long> {

    @Override
    @Query("select t from Taco t join t.ingredients i on i.id = :#{#ingredientId.value()} ")
    Set<Taco> findByIngredientId(IngredientId ingredientId);
}
