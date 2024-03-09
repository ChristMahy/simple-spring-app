package cmahy.webapp.resource.impl.adapter.config;

import cmahy.webapp.resource.impl.adapter.ui.theme.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UIConfigurer implements WebMvcConfigurer {

    @Bean
    public ThemeModeResolver themeModeResolver() {
        return new CookieThemeModeResolver();
    }

    @Bean
    public ResourceBundleThemeModeSource themeModeSource() {
        ResourceBundleThemeModeSource source = new ResourceBundleThemeModeSource();

        source.setBasenamePrefix("theme");

        return source;
    }

    @Bean
    @RequestScope
    public ThemeMode themeMode(
        HttpServletRequest request,
        ThemeModeResolver resolver,
        ThemeModeSource source
    ) {
        return source.themeMode(
            resolver.resolveThemeModeName(request)
        );
    }
}
