package cmahy.webapp.resource.ui;

import cmahy.webapp.resource.ui.vo.input.RegistrationInputApiVo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/register")
public interface RegistrationApi {

    @GetMapping
    String registration(Model model);

    @PostMapping
    String processRegistration(
        Model model,
        @Valid @ModelAttribute(name = "registrationInput") RegistrationInputApiVo registrationInput,
        BindingResult errors
    );
}
