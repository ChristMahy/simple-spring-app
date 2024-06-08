package cmahy.webapp.resource.impl.adapter.api.taco.shop;

import cmahy.common.entity.page.EntityPageableBuilder;
import cmahy.webapp.resource.api.taco.shop.IngredientApi;
import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientCreateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientUpdateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientPageOutputApiVo;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.input.*;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output.IngredientOutputAppMapper;
import cmahy.webapp.resource.impl.adapter.api.taco.shop.mapper.output.IngredientPageOutputAppMapper;
import cmahy.webapp.resource.impl.adapter.config.properties.PaginationProperties;
import cmahy.webapp.resource.impl.application.taco.shop.command.*;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetAllIngredientPagedQuery;
import cmahy.webapp.resource.impl.application.vo.input.PageableInputAppVo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientApiImpl implements IngredientApi {

    private final PaginationProperties paginationProperties;
    private final GetAllIngredientPagedQuery getAllIngredientPagedQuery;
    private final IngredientPageOutputAppMapper ingredientPageOutputAppMapper;
    private final IngredientApiIdMapper ingredientApiIdMapper;
    private final IngredientCreateInputApiMapper ingredientCreateInputApiMapper;
    private final IngredientUpdateInputApiMapper ingredientUpdateInputApiMapper;
    private final CreateIngredientCommand createIngredientCommand;
    private final PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand;
    private final DeleteAnIngredientCommand deleteAnIngredientCommand;
    private final IngredientOutputAppMapper ingredientOutputAppMapper;

    public IngredientApiImpl(
        PaginationProperties paginationProperties,
        GetAllIngredientPagedQuery getAllIngredientPagedQuery,
        IngredientPageOutputAppMapper ingredientPageOutputAppMapper,
        IngredientApiIdMapper ingredientApiIdMapper,
        IngredientCreateInputApiMapper ingredientCreateInputApiMapper,
        IngredientUpdateInputApiMapper ingredientUpdateInputApiMapper,
        CreateIngredientCommand createIngredientCommand,
        PartialUpdatingAnIngredientCommand partialUpdatingAnIngredientCommand,
        DeleteAnIngredientCommand deleteAnIngredientCommand,
        IngredientOutputAppMapper ingredientOutputAppMapper
    ) {
        this.paginationProperties = paginationProperties;
        this.getAllIngredientPagedQuery = getAllIngredientPagedQuery;
        this.ingredientPageOutputAppMapper = ingredientPageOutputAppMapper;
        this.ingredientApiIdMapper = ingredientApiIdMapper;
        this.ingredientCreateInputApiMapper = ingredientCreateInputApiMapper;
        this.ingredientUpdateInputApiMapper = ingredientUpdateInputApiMapper;
        this.createIngredientCommand = createIngredientCommand;
        this.partialUpdatingAnIngredientCommand = partialUpdatingAnIngredientCommand;
        this.deleteAnIngredientCommand = deleteAnIngredientCommand;
        this.ingredientOutputAppMapper = ingredientOutputAppMapper;
    }

    @Override
    public IngredientPageOutputApiVo getAll(
        Integer pageNumber,
        Integer pageSize
    ) {
        PageableInputAppVo pageable = EntityPageableBuilder.<PageableInputAppVo>instance(paginationProperties.pageSize())
            .withPageSize(pageSize)
            .withPageNumber(pageNumber)
            .build(PageableInputAppVo.class);

        return ingredientPageOutputAppMapper.map(
            getAllIngredientPagedQuery.execute(pageable)
        );
    }

    @Override
    public IngredientOutputApiVo create(IngredientCreateApiVo input) {
        return ingredientOutputAppMapper.map(
            createIngredientCommand.execute(
                ingredientCreateInputApiMapper.map(input)
            )
        );
    }

    @Override
    public IngredientOutputApiVo update(IngredientApiId id, IngredientUpdateApiVo input) {
        return ingredientOutputAppMapper.map(
            partialUpdatingAnIngredientCommand.execute(
                ingredientApiIdMapper.map(id), ingredientUpdateInputApiMapper.map(input)
            )
        );
    }

    @Override
    public void delete(IngredientApiId id) {
        deleteAnIngredientCommand.execute(
            ingredientApiIdMapper.map(id)
        );
    }
}
