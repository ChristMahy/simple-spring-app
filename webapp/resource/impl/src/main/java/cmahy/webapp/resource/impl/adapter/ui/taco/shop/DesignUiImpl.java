package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.adapter.ui.taco.shop.mapper.output.IngredientOutputUiMapper;
import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.shop.DesignUi;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputUiVo;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputUiVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DesignUiImpl implements DesignUi {

    private static final Logger LOG = LoggerFactory.getLogger(DesignUiImpl.class);

    private final GetIngredientByTypeQuery ingredientByTypeQuery;
    private final IngredientOutputUiMapper ingredientOutputMapper;

    public DesignUiImpl(
        GetIngredientByTypeQuery ingredientByTypeQuery,
        IngredientOutputUiMapper ingredientOutputMapper
    ) {
        this.ingredientByTypeQuery = ingredientByTypeQuery;
        this.ingredientOutputMapper = ingredientOutputMapper;
    }

    @ModelAttribute(name = "wrap")
    public Set<IngredientOutputUiVo> wrap() {
        return ingredientByTypeQuery.execute(Ingredient.Type.WRAP).stream()
            .map(ingredientOutputMapper::map).collect(Collectors.toSet());
    }

    @ModelAttribute(name = "protein")
    public Set<IngredientOutputUiVo> protein() {
        return ingredientByTypeQuery.execute(Ingredient.Type.PROTEIN).stream()
            .map(ingredientOutputMapper::map).collect(Collectors.toSet());
    }

    @ModelAttribute(name = "cheese")
    public Set<IngredientOutputUiVo> cheese() {
        return ingredientByTypeQuery.execute(Ingredient.Type.CHEESE).stream()
            .map(ingredientOutputMapper::map).collect(Collectors.toSet());
    }

    @ModelAttribute(name = "veggies")
    public Set<IngredientOutputUiVo> veggies() {
        return ingredientByTypeQuery.execute(Ingredient.Type.VEGGIES).stream()
            .map(ingredientOutputMapper::map).collect(Collectors.toSet());
    }

    @ModelAttribute(name = "sauce")
    public Set<IngredientOutputUiVo> sauce() {
        return ingredientByTypeQuery.execute(Ingredient.Type.SAUCE).stream()
            .map(ingredientOutputMapper::map).collect(Collectors.toSet());
    }

    @Override
    public String designForm(Model model) {
        model.addAttribute(TACO, TacoInputUiVo.createEmpty());

        if (!model.containsAttribute(TACOS)) {
            model.addAttribute(TACOS, new ArrayList<TacoInputUiVo>());
        }

        return "taco-shop/design";
    }

    @Override
    public String addDesignTaco(
        Model model,
        TacoInputUiVo taco,
        Errors errors,
        List<TacoInputUiVo> tacos
    ) {
        LOG.info("Add taco <{}> to <{}>", taco, tacos);

        if (!errors.hasErrors()) {
            tacos.add(taco);

            model.addAttribute(TACO, TacoInputUiVo.createEmpty());
        }

        return "taco-shop/design";
    }

    @Override
    public String saveDesignTaco(
        TacoInputUiVo taco,
        Errors errors,
        List<TacoInputUiVo> tacos
    ) {
        LOG.info("Create an order with all tacos <{}> and last one <{}>", tacos, taco);

        if (!errors.hasErrors()) {
            tacos.add(taco);
        }

        if (tacos.isEmpty()) {
            return "taco-shop/design";
        }

        return "redirect:" + TacoUriConstant.ClientOrder.CLIENT_ORDER_BASE_URL;
    }
}
