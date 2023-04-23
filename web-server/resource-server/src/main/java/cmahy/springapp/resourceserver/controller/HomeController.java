package cmahy.springapp.resourceserver.controller;

import cmahy.springapp.resourceserver.config.security.UserSecurityDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal Authentication authentication) {
        // authentication's principle could be either through OAuth or via form-based so you have to cast the principle object into User object carefully.
//        if (authentication != null && authentication.isAuthenticated()) {
//            if(authentication.getPrincipal() instanceof UserDetails) {
//                log.info("It was a form based login");
//
//                UserSecurityDetails user = (UserSecurityDetails) authentication.getPrincipal();
//                model.addAttribute("name", user.getUsername());
//
//            } else  {
//                log.info("It was OAuth based login");
//
//                OAuth2User user = (OAuth2User) authentication.getPrincipal();
//                model.addAttribute("name", user.getAttribute("name"));
//            }
//        } else {
//            model.addAttribute("name", "None");
//        }

        return "home";
    }
}
