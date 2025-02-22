package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.impl.adapter.ui.user.mapper.LocalWithDefaultValueRegistrationInputApiVoMapper;
import cmahy.simple.spring.webapp.resource.ui.RegistrationUi;
import cmahy.simple.spring.webapp.resource.ui.vo.input.RegistrationInputUiVo;
import cmahy.webapp.user.kernel.application.command.RegisterUserSecurityCommand;
import cmahy.webapp.user.kernel.exception.RoleNotFoundException;
import cmahy.webapp.user.kernel.exception.UserExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Controller
public class RegistrationUiImpl implements RegistrationUi {

    private final LocalWithDefaultValueRegistrationInputApiVoMapper localWithDefaultValueRegistrationInputApiVoMapper;
    private final RegisterUserSecurityCommand registerUserSecurityCommand;

    public RegistrationUiImpl(
        LocalWithDefaultValueRegistrationInputApiVoMapper localWithDefaultValueRegistrationInputApiVoMapper,
        RegisterUserSecurityCommand registerUserSecurityCommand
    ) {
        this.localWithDefaultValueRegistrationInputApiVoMapper = localWithDefaultValueRegistrationInputApiVoMapper;
        this.registerUserSecurityCommand = registerUserSecurityCommand;
    }

    @Override
    public String registration(Model model) {
        model.addAttribute("registrationInput", new RegistrationInputUiVo(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        ));

        return "registration";
    }

    @Override
    public String processRegistration(
        Model model,
        RegistrationInputUiVo registrationInput,
        BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return "registration";
        }

        try {
            registerUserSecurityCommand.execute(
                localWithDefaultValueRegistrationInputApiVoMapper.map(registrationInput)
            );
        } catch (RoleNotFoundException roleNotFoundException) {
            ObjectError error = new ObjectError("globalError", "registration.error.role.not-found");

            errors.addError(error);

            return "registration";
        } catch (UserExistsException | IllegalStateException e) {
            ObjectError error = new ObjectError("globalError", "registration.error.username.exists");

            errors.addError(error);

            return "registration";
        }

        return "redirect:/login";
    }
}
