package cmahy.webapp.resource.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = { "", "/" })
public interface IndexApi {

    String TOGGLE_THEME = "toggle-theme";

    @GetMapping
    String index();

    @GetMapping(path = TOGGLE_THEME)
    String toggleTheme(
        HttpServletRequest request,
        HttpServletResponse response
    );
}
