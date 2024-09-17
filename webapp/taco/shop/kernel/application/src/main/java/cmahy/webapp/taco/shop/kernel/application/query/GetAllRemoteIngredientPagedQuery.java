package cmahy.webapp.taco.shop.kernel.application.query;

import cmahy.common.annotation.Query;
import cmahy.common.entity.page.EntityPageable;
import cmahy.webapp.taco.shop.kernel.application.mapper.output.IngredientPageOutputMapper;
import cmahy.webapp.taco.shop.kernel.application.repository.IngredientPagingRepository;
import cmahy.webapp.taco.shop.kernel.application.repository.proxy.annotation.RemoteOrDefault;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Query
@Named
public class GetAllRemoteIngredientPagedQuery {

    private static final Logger LOG = LoggerFactory.getLogger(GetAllRemoteIngredientPagedQuery.class);

    private final IngredientPagingRepository ingredientPagingRepository;
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
