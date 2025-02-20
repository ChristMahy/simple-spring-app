package cmahy.simple.spring.webapp.taco.shop.kernel.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;

@Query
@Named
public class GetAllIngredientPagedQuery {

    private final IngredientPagingRepository<Ingredient> ingredientPagingRepository;
    private final IngredientPageOutputMapper ingredientPageOutputMapper;

    public GetAllIngredientPagedQuery(
        IngredientPagingRepository ingredientPagingRepository,
        IngredientPageOutputMapper ingredientPageOutputMapper
    ) {
        this.ingredientPagingRepository = ingredientPagingRepository;
        this.ingredientPageOutputMapper = ingredientPageOutputMapper;
    }

    public IngredientPageOutputVo execute(EntityPageable pageable) throws RequiredException {
        return ingredientPageOutputMapper.map(
            ingredientPagingRepository.findAll(pageable)
        );
    }
}
