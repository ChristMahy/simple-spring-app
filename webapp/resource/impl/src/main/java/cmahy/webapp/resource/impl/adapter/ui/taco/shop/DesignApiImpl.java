package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.ui.taco.shop.DesignApi;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Collections;

@Controller
public class DesignApiImpl implements DesignApi {

    private final GetIngredientByTypeQuery ingredientByTypeQuery;

    public DesignApiImpl(GetIngredientByTypeQuery ingredientByTypeQuery) {
        this.ingredientByTypeQuery = ingredientByTypeQuery;
    }

    @Override
    public String designForm(Model model) {
        model.addAttribute("taco", new TacoInputApiVo("", Collections.emptyList()));

        for (Ingredient.Type value : Ingredient.Type.values()) {
            model.addAttribute(
                value.toString().toLowerCase(),
                ingredientByTypeQuery.execute(value)
            );
        }

        return "taco-shop/design";
    }
}
