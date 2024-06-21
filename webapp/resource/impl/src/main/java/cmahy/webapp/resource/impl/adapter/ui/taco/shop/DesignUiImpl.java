package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.taco.shop.vo.input.TacoInputVo;
import cmahy.webapp.resource.taco.shop.vo.output.IngredientOutputVo;
import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.shop.DesignUi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

@Controller
public class DesignUiImpl implements DesignUi {

    private static final Logger LOG = LoggerFactory.getLogger(DesignUiImpl.class);

    private final GetIngredientByTypeQuery ingredientByTypeQuery;

    public DesignUiImpl(
        GetIngredientByTypeQuery ingredientByTypeQuery
    ) {
        this.ingredientByTypeQuery = ingredientByTypeQuery;
    }

    @ModelAttribute(name = "wrap")
    public Set<IngredientOutputVo> wrap() {
        return ingredientByTypeQuery.execute(Ingredient.Type.WRAP);
    }

    @ModelAttribute(name = "protein")
    public Set<IngredientOutputVo> protein() {
        return ingredientByTypeQuery.execute(Ingredient.Type.PROTEIN);
    }

    @ModelAttribute(name = "cheese")
    public Set<IngredientOutputVo> cheese() {
        return ingredientByTypeQuery.execute(Ingredient.Type.CHEESE);
    }

    @ModelAttribute(name = "veggies")
    public Set<IngredientOutputVo> veggies() {
        return ingredientByTypeQuery.execute(Ingredient.Type.VEGGIES);
    }

    @ModelAttribute(name = "sauce")
    public Set<IngredientOutputVo> sauce() {
        return ingredientByTypeQuery.execute(Ingredient.Type.SAUCE);
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public String designForm(Model model) {
        model.addAttribute(TACO, new TacoInputVo(
            "", new HashSet<>(0)
        ));

        if (!model.containsAttribute(TACOS)) {
            model.addAttribute(TACOS, new ArrayList<TacoInputVo>());
        }

        return "taco-shop/design";
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public String addDesignTaco(
        Model model,
        TacoInputVo taco,
        Errors errors,
        List<TacoInputVo> tacos
    ) {
        LOG.info("Add taco <{}> to <{}>", taco, tacos);

        if (!errors.hasErrors()) {
            tacos.add(taco);

            model.addAttribute(TACO, new TacoInputVo(
                "", new HashSet<>(0)
            ));
        }

        return "taco-shop/design";
    }

    @Override
    @PreAuthorize("hasRole(@preAuthorizeScope.GUEST)")
    public String saveDesignTaco(
        TacoInputVo taco,
        Errors errors,
        List<TacoInputVo> tacos
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
