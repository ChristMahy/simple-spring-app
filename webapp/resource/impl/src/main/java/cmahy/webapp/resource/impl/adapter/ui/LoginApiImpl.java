package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.ui.LoginApi;
import org.springframework.stereotype.Controller;

@Controller
public class LoginApiImpl implements LoginApi {

    @Override
    public String login() {
        return "login";
    }
}
