package cmahy.simple.spring.webapp.taco.shop.kernel.application.query;

import cmahy.simple.spring.common.annotation.Query;
import cmahy.simple.spring.common.entity.page.EntityPageable;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.repository.proxy.annotation.RemoteOrDefault;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.Ingredient;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Query
@Named
public class GetAllRemoteIngredientPagedQuery {

    private static final Logger LOG = LoggerFactory.getLogger(GetAllRemoteIngredientPagedQuery.class);

    private final IngredientPagingRepository<Ingredient> ingredientPagingRepository;
    private final IngredientPageOutputMapper ingredientPageOutputMapper;

    public GetAllRemoteIngredientPagedQuery(
        @RemoteOrDefault IngredientPagingRepository ingredientPagingRepository,
        IngredientPageOutputMapper ingredientPageOutputMapper
    ) {
        this.ingredientPagingRepository = ingredientPagingRepository;
        this.ingredientPageOutputMapper = ingredientPageOutputMapper;
    }

    public IngredientPageOutputVo execute(EntityPageable pageable) throws RequiredException {
        LOG.info("Getting all ingredients from <{}>", ingredientPagingRepository.getClass().getSimpleName());

        return ingredientPageOutputMapper.map(
            ingredientPagingRepository.findAll(pageable)
        );
    }
}
