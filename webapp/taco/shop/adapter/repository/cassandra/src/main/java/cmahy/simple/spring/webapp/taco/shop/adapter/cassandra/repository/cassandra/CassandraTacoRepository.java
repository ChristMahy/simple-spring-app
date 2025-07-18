package cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.repository.cassandra;

import cmahy.simple.spring.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraTaco;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.TacoId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraTacoRepository extends CassandraRepository<CassandraTaco, UUID> {

    @Query("select * from taco where ingredientIds contains ?0 allow filtering")
    Set<CassandraTaco> findByIngredientId(UUID ingredientId);

    default Set<CassandraTaco> findByIngredientId(IngredientId ingredientId) {
        return this.findByIngredientId(ingredientId.value());
    }

    default List<CassandraTaco> findAllById(List<TacoId> tacoIds) {
        return this.findAllById(tacoIds.stream().map(TacoId::value).toList());
    }
}
