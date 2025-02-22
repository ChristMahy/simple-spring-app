package cmahy.simple.spring.webapp.resource.ui.taco.shop;

import cmahy.simple.spring.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.simple.spring.webapp.taco.shop.kernel.vo.input.TacoInputVo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = TacoUriConstant.Design.DESIGN_BASE_URL)
@SessionAttributes(names = DesignUi.TACOS)
public interface DesignUi {

    String TACO = "taco";
    String TACOS = "tacos";

    @GetMapping
    String designForm(Model model);

    @PostMapping(params = "action=add")
    String addDesignTaco(
        Model model,
        @Valid @ModelAttribute(TACO) TacoInputVo taco,
        Errors errors,
        @ModelAttribute(TACOS) List<TacoInputVo> tacos
    );

    @PostMapping(params = "action=create")
    String saveDesignTaco(
        @Valid @ModelAttribute(TACO) TacoInputVo taco,
        Errors errors,
        @ModelAttribute(TACOS) List<TacoInputVo> tacos
    );
}
