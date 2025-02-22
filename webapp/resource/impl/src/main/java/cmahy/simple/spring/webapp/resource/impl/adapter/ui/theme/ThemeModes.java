package cmahy.simple.spring.webapp.resource.impl.adapter.ui.theme;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Locale;

@Service
@RequestScope
public class ThemeModes {

    private final ThemeMode themeMode;

    public ThemeModes(
        HttpServletRequest request,
        ThemeModeResolver resolver,
        ThemeModeSource source
    ) {
        this.themeMode = source.themeMode(
            resolver.resolveThemeModeName(request)
        );
    }

    public String code(String name) {
        return this.themeMode.messageSource().getMessage(name, null, "", Locale.FRENCH);
    }
}
