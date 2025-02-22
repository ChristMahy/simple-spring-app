package cmahy.simple.spring.webapp.resource.ui;

import cmahy.simple.spring.webapp.resource.ui.vo.input.RegistrationInputUiVo;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/register")
public interface RegistrationUi {

    @GetMapping
    String registration(Model model);

    @PostMapping
    String processRegistration(
        Model model,
        @Valid @ModelAttribute(name = "registrationInput") RegistrationInputUiVo registrationInput,
        BindingResult errors
    );
}
