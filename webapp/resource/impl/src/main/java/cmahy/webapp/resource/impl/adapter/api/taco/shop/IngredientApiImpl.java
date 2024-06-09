package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.entity.page.EntityPageableBuilder;
import cmahy.webapp.resource.api.taco.shop.IngredientApi;
import cmahy.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.webapp.resource.impl.application.taco.shop.command.*;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetAllIngredientPagedQuery;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientPageOutputVo;
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
    public IngredientPageOutputVo getAll(
        Integer pageNumber,
        Integer pageSize
    ) {
        PageableInputAppVo pageable = EntityPageableBuilder.<PageableInputAppVo>instance(paginationProperties.pageSize())
            .withPageSize(pageSize)
            .withPageNumber(pageNumber)
            .build(PageableInputAppVo.class);

        return getAllIngredientPagedQuery.execute(pageable);
    }

    @Override
    public IngredientOutputVo create(IngredientCreateInputVo input) {
        return createIngredientCommand.execute(input);
    }

    @Override
    public IngredientOutputVo update(IngredientId id, IngredientUpdateInputVo input) {
        return partialUpdatingAnIngredientCommand.execute(id, input);
    }

    @Override
    public void delete(IngredientId id) {
        deleteAnIngredientCommand.execute(id);
    }
}
