package cmahy.webapp.resource.api.taco.shop;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.api.taco.shop.vo.id.IngredientApiId;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientCreateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.input.IngredientUpdateApiVo;
import cmahy.webapp.resource.api.taco.shop.vo.output.IngredientOutputApiVo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cmahy.webapp.resource.api.UriConstant.BASE_V1;

@RequestMapping(path = IngredientApi.BASE_URL)
public interface IngredientApi {
    String BASE_URL = BASE_V1 + "/ingredient";

    String PATH_VARIABLE_INGREDIENT_ID = "{id}";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    EntityPage<IngredientOutputApiVo> getAll(
        @RequestParam(name = "page-number") Integer pageNumber,
        @RequestParam(name = "page-size") Integer pageSize
    );

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputApiVo create(@Valid @RequestBody IngredientCreateApiVo input);

    @PatchMapping(path = "/" + PATH_VARIABLE_INGREDIENT_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputApiVo update(
        @PathVariable IngredientApiId id,
        @Valid @RequestBody IngredientUpdateApiVo input
    );

    @DeleteMapping(path = "/" + PATH_VARIABLE_INGREDIENT_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable IngredientApiId id);
}
