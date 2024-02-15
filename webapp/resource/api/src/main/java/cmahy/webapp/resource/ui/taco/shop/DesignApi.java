package cmahy.webapp.resource.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.TacoUriConstant;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = TacoUriConstant.Design.DESIGN_BASE_URL)
public interface DesignApi {

    @GetMapping
    String designForm(Model model);
}
