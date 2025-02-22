package cmahy.simple.spring.webapp.resource.impl.adapter.ui;

import cmahy.simple.spring.webapp.resource.ui.LoginUi;
import org.springframework.stereotype.Controller;

@Controller
public class LoginUiImpl implements LoginUi {

    @Override
    public String login() {
        return "login";
    }
}
