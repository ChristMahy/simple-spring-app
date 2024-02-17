package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.ui.taco.shop.DesignApi;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputApiVo;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import java.util.*;

@Controller
public class DesignApiImpl implements DesignApi {

    private static final Logger LOG = LoggerFactory.getLogger(DesignApiImpl.class);

    private final GetIngredientByTypeQuery ingredientByTypeQuery;

    public DesignApiImpl(
        GetIngredientByTypeQuery ingredientByTypeQuery
    ) {
        this.ingredientByTypeQuery = ingredientByTypeQuery;
    }

    @Override
    public String designForm(Model model) {
        model.addAttribute("taco", new TacoInputApiVo("", new ArrayList<>()));

        for (Ingredient.Type value : Ingredient.Type.values()) {
            model.addAttribute(
                value.toString().toLowerCase(),
                ingredientByTypeQuery.execute(value)
            );
        }

        return "taco-shop/design";
    }

    @Override
    public String saveDesignTaco(
        Model model,
        TacoInputApiVo taco,
        Errors errors
    ) {
        LOG.info("Processing taco <{}>", taco);

        if (errors.hasErrors()) {
            return "/taco/design";
        }

        model.addAttribute(
            "tacoOrder",
            new ClientOrderInputApiVo(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                List.of(taco)
            )
        );

        return "redirect:/taco/design";
    }
}
