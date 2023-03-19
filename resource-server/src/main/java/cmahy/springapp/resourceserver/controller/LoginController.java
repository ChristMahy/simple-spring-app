package cmahy.springapp.resourceserver.controller;

import cmahy.springapp.resourceserver.config.security.UserSecurityDetails;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final OAuth2AuthorizedClientService authclientService;

    public LoginController(OAuth2AuthorizedClientService authclientService) {
        this.authclientService = authclientService;
    }

    @RequestMapping
    public String login() {
        return "login";
    }

    @RequestMapping("/form-success")
    public String formLoginSuccess(Model model, UsernamePasswordAuthenticationToken authentication) {
        LOG.info("Login with normal form is a success !!!!");

        UserSecurityDetails userSecurityDetails = (UserSecurityDetails) authentication.getPrincipal();

        model.addAttribute("name", userSecurityDetails.getUsername());

        return "home";
    }

    @RequestMapping("/oauth2-success")
    public String oauth2LoginSuccess(Model model, OAuth2AuthenticationToken authentication) {
        // fetching the client details and user details and then adding them into
        LOG.debug(authentication.getAuthorizedClientRegistrationId()); // client name like facebook, google etc.
        LOG.debug(authentication.getName()); // facebook/google userId

        //	1.Fetching User Info
        OAuth2User user = authentication.getPrincipal(); // When you work with Spring OAuth it gives you OAuth2User instead of UserDetails

        LOG.debug("userId: " + user.getName()); // returns the userId of facebook
        // getAttributes map Contains User details like name, email etc// print the whole map for more details
        LOG.debug("Email:" + user.getAttributes().get("email"));

        //2. Just in case if you want to obtain User's auth token value, refresh token, expiry date etc you can use below snippet
        OAuth2AuthorizedClient client = authclientService
            .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
            );

        LOG.debug("Token Value" + client.getAccessToken().getTokenValue());

        //3. Now you have full control on users data.You can either see if user is not present in Database then store it and
        // send welcome email for the first time
        model.addAttribute("name", user.getAttribute("name"));

        return "home";
    }
}
