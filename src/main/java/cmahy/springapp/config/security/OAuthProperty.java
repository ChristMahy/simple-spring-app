package cmahy.springapp.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static cmahy.springapp.config.security.OAuth2ConfigConstant.PROPERTY_PREFIX;

@Component
@ConfigurationProperties(prefix = PROPERTY_PREFIX)
public class OAuthProperty {
    private boolean enable;

    protected OAuthProperty() {
        this.enable = false;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
