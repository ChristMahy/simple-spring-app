package cmahy.webapp.taco.shop.adapter.cassandra.repository;

import cmahy.webapp.taco.shop.kernel.application.repository.TacoRepository;
import cmahy.webapp.taco.shop.kernel.domain.Taco;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Primary
@Repository
public interface TacoRepositoryImpl extends
    TacoRepository,
    CassandraRepository<Taco, Long> {

    @Override
    @Query(
        value = "select t from Taco t join t.ingredients i on i.id = :#{#ingredientId.value()} "
    )
    Set<Taco> findByIngredientId(IngredientId ingredientId);
}
