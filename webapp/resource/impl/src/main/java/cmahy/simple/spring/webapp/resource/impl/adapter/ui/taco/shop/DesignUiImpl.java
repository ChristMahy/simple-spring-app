package cmahy.simple.spring.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.simple.spring.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.simple.spring.webapp.resource.ui.taco.shop.DesignUi;
import cmahy.simple.spring.webapp.taco.shop.kernel.application.query.GetIngredientByTypeQuery;
import cmahy.simple.spring.webapp.taco.shop.kernel.domain.IngredientType;
import cmahy.simple.spring.webapp.taco.shop.kernel.exception.RequiredException;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.output.IngredientOutputVo;
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
    public Set<IngredientOutputVo> wrap() throws RequiredException {
        return ingredientByTypeQuery.execute(IngredientType.WRAP);
    }

    @ModelAttribute(name = "protein")
    public Set<IngredientOutputVo> protein() throws RequiredException {
        return ingredientByTypeQuery.execute(IngredientType.PROTEIN);
    }

    @ModelAttribute(name = "cheese")
    public Set<IngredientOutputVo> cheese() throws RequiredException {
        return ingredientByTypeQuery.execute(IngredientType.CHEESE);
    }

    @ModelAttribute(name = "veggies")
    public Set<IngredientOutputVo> veggies() throws RequiredException {
        return ingredientByTypeQuery.execute(IngredientType.VEGGIES);
    }

    @ModelAttribute(name = "sauce")
    public Set<IngredientOutputVo> sauce() throws RequiredException {
        return ingredientByTypeQuery.execute(IngredientType.SAUCE);
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
