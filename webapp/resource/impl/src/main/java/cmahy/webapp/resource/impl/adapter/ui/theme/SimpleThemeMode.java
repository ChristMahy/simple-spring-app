package cmahy.webapp.resource.impl.adapter.ui.theme;

import org.springframework.context.MessageSource;
import org.springframework.util.Assert;

public record SimpleThemeMode(
    String name,
    MessageSource messageSource
) implements ThemeMode {

    public SimpleThemeMode {
        Assert.notNull(name, "Name should not be null");
        Assert.notNull(messageSource, "Message source should not be null");
    }
}
