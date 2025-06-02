package cmahy.simple.spring.webapp.resource.impl.adapter.ui;

import cmahy.simple.spring.webapp.resource.impl.adapter.security.repository.SecurityOAuth2ClientRegistrationRepository;
import cmahy.simple.spring.webapp.resource.ui.LoginUi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class LoginUiImpl implements LoginUi {

    private final SecurityOAuth2ClientRegistrationRepository securityOAuth2ClientRegistrationRepository;

    public LoginUiImpl(
        SecurityOAuth2ClientRegistrationRepository securityOAuth2ClientRegistrationRepository
    ) {
        this.securityOAuth2ClientRegistrationRepository = securityOAuth2ClientRegistrationRepository;
    }

    @Override
    public String login(Model model) {

        model.addAttribute("registrations", securityOAuth2ClientRegistrationRepository.findAllAvailableRegistrations());

        return "login";
    }
}
