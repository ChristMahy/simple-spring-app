package cmahy.webapp.resource.api.taco.shop;

import cmahy.common.entity.page.EntityPage;
import cmahy.webapp.resource.taco.shop.id.IngredientId;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientCreateInputVo;
import cmahy.webapp.resource.taco.shop.vo.input.IngredientUpdateInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
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
    EntityPage<IngredientOutputVo> getAll(
        @RequestParam(name = "page-number") Integer pageNumber,
        @RequestParam(name = "page-size") Integer pageSize
    );

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputVo create(@Valid @RequestBody IngredientCreateInputVo input);

    @PatchMapping(path = "/" + PATH_VARIABLE_INGREDIENT_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputVo update(
        @PathVariable IngredientId id,
        @Valid @RequestBody IngredientUpdateInputVo input
    );

    @DeleteMapping(path = "/" + PATH_VARIABLE_INGREDIENT_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable IngredientId id);
}
