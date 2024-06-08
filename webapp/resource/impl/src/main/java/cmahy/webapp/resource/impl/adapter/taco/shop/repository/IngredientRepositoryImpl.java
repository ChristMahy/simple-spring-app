package cmahy.webapp.resource.impl.adapter.taco.shop.repository;

import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientRepository;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.impl.domain.taco.page.IngredientPage;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
