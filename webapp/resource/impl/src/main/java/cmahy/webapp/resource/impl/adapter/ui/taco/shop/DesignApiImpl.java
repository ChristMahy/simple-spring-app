package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.impl.application.taco.shop.query.GetIngredientByTypeQuery;
import cmahy.webapp.resource.impl.application.taco.shop.vo.output.IngredientOutputAppVo;
import cmahy.webapp.resource.impl.domain.taco.Ingredient;
import cmahy.webapp.resource.ui.taco.shop.DesignApi;
import cmahy.webapp.resource.ui.taco.vo.input.ClientOrderInputApiVo;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DesignApiImpl implements DesignApi {

    private static final Logger LOG = LoggerFactory.getLogger(DesignApiImpl.class);

    private static final String TACO = "taco";
    private static final String TACO_ORDER = "tacoOrder";

    private final GetIngredientByTypeQuery ingredientByTypeQuery;

    public DesignApiImpl(
        GetIngredientByTypeQuery ingredientByTypeQuery
    ) {
        this.ingredientByTypeQuery = ingredientByTypeQuery;
    }

    @ModelAttribute("wrap")
    public Set<IngredientOutputAppVo> wrap() {
        return ingredientByTypeQuery.execute(Ingredient.Type.WRAP);
    }

    @ModelAttribute("protein")
    public Set<IngredientOutputAppVo> protein() {
        return ingredientByTypeQuery.execute(Ingredient.Type.PROTEIN);
    }

    @ModelAttribute("cheese")
    public Set<IngredientOutputAppVo> cheese() {
        return ingredientByTypeQuery.execute(Ingredient.Type.CHEESE);
    }

    @ModelAttribute("veggies")
    public Set<IngredientOutputAppVo> veggies() {
        return ingredientByTypeQuery.execute(Ingredient.Type.VEGGIES);
    }

    @ModelAttribute("sauce")
    public Set<IngredientOutputAppVo> sauce() {
        return ingredientByTypeQuery.execute(Ingredient.Type.SAUCE);
    }

    @Override
    public String designForm(Model model, HttpSession session) {
        var tacoOrder = session.getAttribute(TACO_ORDER);
        ClientOrderInputApiVo order;

        if (Objects.nonNull(tacoOrder)) {
            order = (ClientOrderInputApiVo) tacoOrder;
        } else {
            order = ClientOrderInputApiVo.createEmpty();
        }

        model.addAttribute(TACO, TacoInputApiVo.createEmpty());

        session.setAttribute(TACO_ORDER, order);
        updateViewOrderTacosInfos(model, order);

        return "taco-shop/design";
    }

    @Override
    public String addDesignTaco(
        Model model,
        TacoInputApiVo taco,
        Errors errors,
        HttpSession session
    ) {
        var tacoOrder = extractOrderFromSession(session).orElseThrow(() -> new RuntimeException("Taco order session not set !"));

        LOG.info("Add taco <{}> to <{}>", taco, tacoOrder);

        if (!errors.hasErrors()) {
            tacoOrder = tacoOrder.addATaco(taco);

            taco = TacoInputApiVo.createEmpty();

            session.setAttribute(TACO_ORDER, tacoOrder);
            updateViewOrderTacosInfos(model, tacoOrder);
        }

        model.addAttribute(TACO, taco);

        return "taco-shop/design";
    }

    @Override
    public String saveDesignTaco(
        Model model,
        TacoInputApiVo taco,
        Errors errors,
        HttpSession session
    ) {
        var tacoOrder = extractOrderFromSession(session).orElseThrow(() -> new RuntimeException("Taco order session not set !"));

        LOG.info("Create order <{}> with last taco <{}>", tacoOrder, taco);

        if (errors.hasErrors()) {
            model.addAttribute(TACO, taco);

            return "taco-shop/design";
        }

        tacoOrder = tacoOrder.addATaco(taco);

        model.addAttribute(TACO, TacoInputApiVo.createEmpty());

        session.setAttribute(TACO_ORDER, tacoOrder);
        updateViewOrderTacosInfos(model, tacoOrder);

        return "taco-shop/design";
    }

    private Optional<ClientOrderInputApiVo> extractOrderFromSession(HttpSession session) {
        var tacoOrderSession = session.getAttribute(TACO_ORDER);

        if (Objects.nonNull(tacoOrderSession)) {
            return Optional.of((ClientOrderInputApiVo) tacoOrderSession);
        }

        return Optional.empty();
    }

    private void updateViewOrderTacosInfos(Model model, ClientOrderInputApiVo tacoOrder) {
        model.addAttribute(
            "tacosName",
            tacoOrder.tacos()
                .stream()
                .map(TacoInputApiVo::name)
                .collect(Collectors.joining(", "))
        );

        model.addAttribute(
            "tacosSize",
            tacoOrder.tacos().size()
        );
    }
}
