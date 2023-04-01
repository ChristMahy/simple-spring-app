package cmahy.springapp.resourceserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping
    public String login() {
        return "login";
    }
}
