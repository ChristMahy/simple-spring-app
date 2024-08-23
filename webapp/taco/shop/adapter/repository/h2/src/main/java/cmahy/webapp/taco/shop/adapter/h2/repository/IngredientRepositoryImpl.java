package cmahy.webapp.taco.shop.adapter.h2.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.domain.page.IngredientPage;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface IngredientRepositoryImpl extends
    IngredientRepository,
    IngredientPagingRepository,
    JpaRepository<Ingredient, String> {

    @Override
    default IngredientPage findAll(EntityPageable pageable) {
        Page<Ingredient> all = IngredientRepositoryImpl.this.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()));

        return new IngredientPage(
            all.getContent(),
            all.getTotalElements()
        );
    }

    @Override
    default Optional<Ingredient> findById(IngredientId id) {
        return this.findById(id.value());
    }

    @Override
    default void deleteById(IngredientId id) {
        this.deleteById(id.value());
    }
}
