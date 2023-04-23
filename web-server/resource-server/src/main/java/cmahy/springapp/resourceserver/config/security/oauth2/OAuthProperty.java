package cmahy.springapp.resourceserver.config.security.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static cmahy.springapp.resourceserver.config.security.SecurityConfigConstant.OAUTH2_PROPERTY_PREFIX;

@Component
@ConfigurationProperties(prefix = OAUTH2_PROPERTY_PREFIX)
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
