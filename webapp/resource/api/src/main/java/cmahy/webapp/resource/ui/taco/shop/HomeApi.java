package cmahy.webapp.resource.ui.taco.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static cmahy.webapp.resource.ui.taco.TacoUIUriConstant.BASE_URL;

@RequestMapping(BASE_URL)
public interface HomeApi {

    @GetMapping
    String home();
}
