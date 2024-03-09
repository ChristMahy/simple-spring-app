package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.impl.adapter.ui.theme.ThemeModeResolver;
import cmahy.webapp.resource.ui.IndexApi;
import jakarta.servlet.http.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

@Controller
public class IndexApiImpl implements IndexApi {

    private static final Logger LOG = LoggerFactory.getLogger(IndexApiImpl.class);

    private final ThemeModeResolver themeModeResolver;

    public IndexApiImpl(ThemeModeResolver themeModeResolver) {
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
