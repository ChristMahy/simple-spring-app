package cmahy.simple.spring.webapp.resource.impl.adapter.ui;

import cmahy.simple.spring.webapp.resource.impl.adapter.ui.theme.ThemeModeResolver;
import cmahy.simple.spring.webapp.resource.ui.IndexUi;
import jakarta.servlet.http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

@Controller
public class IndexUiImpl implements IndexUi {

    private static final Logger LOG = LoggerFactory.getLogger(IndexUiImpl.class);

    private final ThemeModeResolver themeModeResolver;

    public IndexUiImpl(ThemeModeResolver themeModeResolver) {
        this.themeModeResolver = themeModeResolver;
    }

    @Override
    public String index() {
        return "index";
    }

    @Override
    public String toggleTheme(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Cookie cookie = WebUtils.getCookie(request, "theme-mode");
        String themeModeValue = "dark-theme";

        if (Objects.nonNull(cookie)) {
            themeModeValue = StringUtils.equals(themeModeValue, cookie.getValue()) ? "default-theme" : themeModeValue;
        }

        LOG.info("Set theme mode <{}>", themeModeValue);

        this.themeModeResolver.setThemeMode(request, response, themeModeValue);

        return "redirect:/";
    }
}
