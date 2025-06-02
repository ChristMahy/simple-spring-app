package cmahy.simple.spring.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.simple.spring.common.entity.page.DefaultEntityPageableImpl;
import cmahy.simple.spring.common.entity.page.EntityPageableBuilder;
import cmahy.simple.spring.webapp.resource.api.taco.shop.IngredientApi;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.command.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.query.GetAllIngredientPagedQuery;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.id.IngredientId;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.ingredient.*;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientCreateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.IngredientUpdateInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientPageOutputVo;
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
    @PreAuthorize("hasAuthority(@preAuthorizeAuthorities.ingredient.READ)")
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
    @PreAuthorize("hasAuthority(@preAuthorizeAuthorities.ingredient.WRITE)")
    public IngredientOutputVo create(IngredientCreateInputVo input) throws IngredientDuplicateException, RequiredException {
        return createIngredientCommand.execute(input);
    }

    @Override
    @PreAuthorize("hasAuthority(@preAuthorizeAuthorities.ingredient.WRITE)")
    public IngredientOutputVo update(IngredientId id, IngredientUpdateInputVo input) throws IngredientNotFoundException, RequiredException {
        return partialUpdatingAnIngredientCommand.execute(id, input);
    }

    @Override
    @PreAuthorize("hasAuthority(@preAuthorizeAuthorities.ingredient.DELETE)")
    public void delete(IngredientId id) throws IngredientUsageElementOnDeletionException {
        deleteAnIngredientCommand.execute(id);
    }
}
