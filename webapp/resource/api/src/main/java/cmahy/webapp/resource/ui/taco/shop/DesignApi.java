package cmahy.webapp.resource.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import cmahy.webapp.resource.ui.taco.vo.input.TacoInputApiVo;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = TacoUriConstant.Design.DESIGN_BASE_URL)
public interface DesignApi {

    @GetMapping
    String designForm(Model model, HttpSession session);

    @PostMapping(params = "action=add")
    String addDesignTaco(
        Model model,
        @Valid TacoInputApiVo taco,
        Errors errors,
        HttpSession session
    );

    @PostMapping(params = "action=create")
    String saveDesignTaco(
        Model model,
        @Valid TacoInputApiVo taco,
        Errors errors,
        HttpSession session
    );
}
