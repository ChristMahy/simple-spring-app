package cmahy.webapp.resource.api.taco.shop;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<IngredientOutputApiVo> getAll();

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputApiVo create(@Valid @RequestBody IngredientCreateApiVo input);

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    IngredientOutputApiVo update(@Valid @RequestBody IngredientUpdateApiVo input);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable IngredientApiId id);
}
