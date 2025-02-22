package cmahy.simple.spring.webapp.resource.ui.taco.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static cmahy.simple.spring.webapp.resource.ui.taco.TacoUriConstant.BASE_URL;

@RequestMapping(BASE_URL)
public interface HomeUi {

    @GetMapping
    String home();
}
