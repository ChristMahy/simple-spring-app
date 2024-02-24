package cmahy.webapp.resource.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import cmahy.webapp.resource.ui.taco.vo.output.IngredientOutputApiVo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping(path = TacoUriConstant.Design.DESIGN_BASE_URL)
@SessionAttributes(names = DesignApi.TACOS)
public interface DesignApi {

    String TACO = "taco";
    String TACOS = "tacos";

    @ModelAttribute(name = "wrap")
    public Set<IngredientOutputApiVo> wrap();

    @ModelAttribute(name = "protein")
    public Set<IngredientOutputApiVo> protein();

    @ModelAttribute(name = "cheese")
    public Set<IngredientOutputApiVo> cheese();

    @ModelAttribute(name = "veggies")
    public Set<IngredientOutputApiVo> veggies();

    @ModelAttribute(name = "sauce")
    public Set<IngredientOutputApiVo> sauce();

    @GetMapping
    String designForm(Model model);

    @PostMapping(params = "action=add")
    String addDesignTaco(
        Model model,
        @Valid @ModelAttribute(TACO) TacoInputApiVo taco,
        Errors errors,
        @ModelAttribute(TACOS) List<TacoInputApiVo> tacos
    );

    @PostMapping(params = "action=create")
    String saveDesignTaco(
        @Valid @ModelAttribute(TACO) TacoInputApiVo taco,
        Errors errors,
        @ModelAttribute(TACOS) List<TacoInputApiVo> tacos
    );
}
