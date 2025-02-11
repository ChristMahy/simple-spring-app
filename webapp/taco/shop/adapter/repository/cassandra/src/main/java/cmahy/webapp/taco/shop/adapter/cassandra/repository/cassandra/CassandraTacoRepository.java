package cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra;

import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.domain.id.TacoId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraTacoRepository extends CassandraRepository<CassandraTaco, UUID> {

    @Query("select t from CassandraTaco t where t.ingredientIds contains #{#ingredientId.value()} allow filtering")
    Set<CassandraTaco> findByIngredientId(IngredientId ingredientId);

    default List<CassandraTaco> findAllById(List<TacoId> tacoIds) {
        return this.findAllById(tacoIds.stream().map(TacoId::value).toList());
    }
}
