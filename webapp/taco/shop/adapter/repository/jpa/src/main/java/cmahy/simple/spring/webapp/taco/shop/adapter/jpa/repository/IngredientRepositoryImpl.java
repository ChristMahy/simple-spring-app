package cmahy.simple.spring.webapp.taco.shop.adapter.jpa.repository;

import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.adapter.jpa.entity.domain.JpaIngredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.page.IngredientPage;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public interface IngredientRepositoryImpl extends
    IngredientRepository<JpaIngredient>,
    IngredientPagingRepository<JpaIngredient>,
    JpaRepository<JpaIngredient, UUID> {

    @Override
    default IngredientPage<JpaIngredient> findAll(EntityPageable pageable) {
        Page<JpaIngredient> all = IngredientRepositoryImpl.this.findAll(PageRequest.of(pageable.pageNumber(), pageable.pageSize()));

        return new IngredientPage<>(
            all.getContent(),
            all.getTotalElements()
        );
    }

    @Override
    default Optional<JpaIngredient> findById(IngredientId id) {
        return this.findById(id.value());
    }

    @Override
    default void deleteById(IngredientId id) {
        this.deleteById(id.value());
    }
}
