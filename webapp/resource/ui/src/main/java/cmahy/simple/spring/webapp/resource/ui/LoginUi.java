package cmahy.simple.spring.webapp.resource.ui;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/login")
public interface LoginUi {

    @GetMapping
    String login(Model model);

}
