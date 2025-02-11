package cmahy.webapp.taco.shop.adapter.cassandra.repository.cassandra;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.adapter.cassandra.entity.domain.CassandraIngredient;
import cmahy.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import org.springframework.data.cassandra.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CassandraIngredientRepository extends CassandraRepository<CassandraIngredient, UUID> {

    Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "name");

    default Slice<CassandraIngredient> findAll(EntityPageable pageable) {
        return Integer.MAX_VALUE == pageable.pageSize() ? new SliceImpl<>(findAll(), Pageable.unpaged(DEFAULT_SORT), false) : findAll(
            PageRequest.of(pageable.pageNumber(), pageable.pageSize(), DEFAULT_SORT)
        );
    }

    default Optional<CassandraIngredient> findById(IngredientId id) {
        return this.findById(id.value());
    }

    default void deleteById(IngredientId id) {
        this.deleteById(id.value());
    }

    @AllowFiltering
    Set<CassandraIngredient> findByType(IngredientType type);

    @AllowFiltering
    Optional<CassandraIngredient> findByNameAndType(String name, IngredientType type);

    default List<CassandraIngredient> findAllById(Set<IngredientId> ingredientIds) {
        return this.findAllById(ingredientIds.stream().map(IngredientId::value).toList());
    }
}
