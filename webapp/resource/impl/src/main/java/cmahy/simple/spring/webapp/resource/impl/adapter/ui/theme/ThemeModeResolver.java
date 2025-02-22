package cmahy.simple.spring.webapp.resource.impl.adapter.ui.theme;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ThemeModeResolver {

    String resolveThemeModeName(HttpServletRequest request);

    void setThemeMode(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable String themeMode);
}
