package cmahy.webapp.resource.impl.adapter.ui.theme;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

public class CookieThemeModeResolver implements ThemeModeResolver {

    public static final String THEME_MODE_REQUEST_ATTRIBUTE = CookieThemeModeResolver.class.getName() + "THEME_MODE";

    private final String defaultThemeName;
    private ResponseCookie cookie;

    public CookieThemeModeResolver() {
        this(
            "theme-mode",
            "default-theme"
        );
    }

    public CookieThemeModeResolver(
        String cookieName,
        String defaultThemeName
    ) {
        this.defaultThemeName = defaultThemeName;
        this.cookie = ResponseCookie
            .from(cookieName)
            .path("/")
            .secure(true)
            .httpOnly(true)
            .maxAge(7 * 24 * 60 * 60)
            .build();
    }

    @Override
    public String resolveThemeModeName(HttpServletRequest request) {
        if (Objects.isNull(request.getAttribute(THEME_MODE_REQUEST_ATTRIBUTE))) {
            Cookie cookieFromRequest = WebUtils.getCookie(request, cookie.getName());

            if (Objects.nonNull(cookieFromRequest)) {
                request.setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, cookieFromRequest.getValue());
            } else {
                request.setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, defaultThemeName);
            }
        }

        return (String) request.getAttribute(THEME_MODE_REQUEST_ATTRIBUTE);
    }

    @Override
    public void setThemeMode(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String themeMode) {
        Assert.notNull(response, "HttpServletResponse is required for CookieLocaleResolver");

        themeMode = StringUtils.isBlank(themeMode) ? defaultThemeName : themeMode;

        cookie = cookie.mutate().value(themeMode).build();

        response.addHeader("Set-Cookie", this.cookie.toString());
        request.setAttribute(THEME_MODE_REQUEST_ATTRIBUTE, themeMode);
    }
}
