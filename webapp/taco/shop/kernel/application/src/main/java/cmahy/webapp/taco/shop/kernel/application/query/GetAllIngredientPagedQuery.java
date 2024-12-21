package cmahy.webapp.taco.shop.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
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
