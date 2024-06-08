package cmahy.webapp.resource.impl.application.taco.shop.query;

import cmahy.common.annotation.Query;
import cmahy.webapp.resource.impl.application.taco.shop.mapper.output.IngredientPageOutputMapper;
import cmahy.webapp.resource.impl.application.taco.shop.repository.IngredientPagingRepository;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientPageOutputAppVo;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import jakarta.inject.Named;

@Query
@Named
public class GetAllIngredientPagedQuery {

    private final IngredientPagingRepository ingredientPagingRepository;
    private final IngredientPageOutputMapper ingredientPageOutputMapper;

    public GetAllIngredientPagedQuery(
        IngredientPagingRepository ingredientPagingRepository,
        IngredientPageOutputMapper ingredientPageOutputMapper
    ) {
        this.ingredientPagingRepository = ingredientPagingRepository;
        this.ingredientPageOutputMapper = ingredientPageOutputMapper;
    }

    public IngredientPageOutputAppVo execute(PageableInputAppVo pageable) {
        return ingredientPageOutputMapper.map(
            ingredientPagingRepository.findAll(pageable)
        );
    }
}
