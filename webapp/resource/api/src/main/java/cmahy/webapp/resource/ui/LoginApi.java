package cmahy.webapp.resource.ui;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/login")
public interface LoginApi {

    @RequestMapping
    String login();
}
