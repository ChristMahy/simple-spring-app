package cmahy.simple.spring.webapp.resource.impl.adapter.ui.theme;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceBundleThemeModeSource implements ThemeModeSource, BeanClassLoaderAware {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleThemeModeSource.class);

    private Optional<ClassLoader> beanClassLoader;
    private Path basenamePrefix = Path.of("");
    private final Map<String, ThemeMode> themeCache = new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = Optional.ofNullable(classLoader);
    }

    public void setBasenamePrefix(String basenamePrefix) {
        this.basenamePrefix = Path.of(StringUtils.isNotBlank(basenamePrefix) ? basenamePrefix : "");
    }

    @Override
    public ThemeMode themeMode(String themeName) {
        ThemeMode theme = this.themeCache.get(themeName);

        if (Objects.isNull(theme)) {
            synchronized (this.themeCache) {
                theme = this.themeCache.get(themeName);

                if (Objects.isNull(theme)) {
                    Path baseName = this.basenamePrefix.resolve(themeName);

                    MessageSource messageSource = this.createMessageSource(baseName);

                    theme = new SimpleThemeMode(themeName, messageSource);
                    this.themeCache.put(themeName, theme);

                    LOG.debug("<{}> created: name <{}>, basename <{}>", ThemeMode.class.getName(), themeName, baseName);
                }
            }
        }

        return theme;
    }

    protected MessageSource createMessageSource(Path basename) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(basename.toString());

        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        beanClassLoader.ifPresent(messageSource::setBeanClassLoader);

        return messageSource;
    }
}
