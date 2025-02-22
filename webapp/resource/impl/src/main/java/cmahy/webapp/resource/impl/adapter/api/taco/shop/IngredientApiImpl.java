package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.entity.page.DefaultEntityPageableImpl;
import cmahy.common.entity.page.EntityPageableBuilder;
import cmahy.simple.spring.webapp.resource.api.taco.shop.IngredientApi;
import cmahy.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.webapp.taco.shop.kernel.application.query.GetAllIngredientPagedQuery;
import cmahy.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientApiImpl implements IngredientApi {

    private final PaginationProperties paginationProperties;
    private final GetAllIngredientPagedQuery getAllIngredientPagedQuery;
    private final CreateIngredientCommand createIngredientCommand;
    private final PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand;
    private final DeleteAnIngredientCommand deleteAnIngredientCommand;

    public IngredientApiImpl(
        PaginationProperties paginationProperties,
        GetAllIngredientPagedQuery getAllIngredientPagedQuery,
        CreateIngredientCommand createIngredientCommand,
        PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand,
        DeleteAnIngredientCommand deleteAnIngredientCommand
    ) {
        this.paginationProperties = paginationProperties;
        this.getAllIngredientPagedQuery = getAllIngredientPagedQuery;
        this.createIngredientCommand = createIngredientCommand;
        this.partialUpdatingAnIngredientCommand = partialUpdatingAnIngredientCommand;
        this.deleteAnIngredientCommand = deleteAnIngredientCommand;
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public IngredientPageOutputVo getAll(
        Integer pageNumber,
        Integer pageSize
    ) throws RequiredException {
        DefaultEntityPageableImpl pageable = EntityPageableBuilder.<DefaultEntityPageableImpl>instance(paginationProperties.pageSize())
            .withPageSize(pageSize)
            .withPageNumber(pageNumber)
            .build(DefaultEntityPageableImpl.class);

        return getAllIngredientPagedQuery.execute(pageable);
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.ADMIN)")
    public IngredientOutputVo create(IngredientCreateInputVo input) throws IngredientDuplicateException, RequiredException {
        return createIngredientCommand.execute(input);
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.ADMIN)")
    public IngredientOutputVo update(IngredientId id, IngredientUpdateInputVo input) throws IngredientNotFoundException, RequiredException {
        return partialUpdatingAnIngredientCommand.execute(id, input);
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.ADMIN)")
    public void delete(IngredientId id) throws IngredientUsageElementOnDeletionException {
        deleteAnIngredientCommand.execute(id);
    }
}
