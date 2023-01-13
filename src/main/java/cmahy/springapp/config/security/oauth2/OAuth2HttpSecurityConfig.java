package cmahy.springapp.config.security.oauth2;

import cmahy.springapp.config.security.HttpSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import static cmahy.springapp.config.security.SecurityConfigConstant.*;

@ConditionalOnProperty(
    prefix = PROPERTY_PREFIX,
    name = OAUTH2_NAME,
    havingValue = OAUTH2_NAME_VALUE
)
@Component
public class OAuth2HttpSecurityConfig implements HttpSecurityConfig {
    private static final Logger LOG = LoggerFactory.getLogger(OAuth2HttpSecurityConfig.class);

    @Override
    public HttpSecurity execute(HttpSecurity httpSecurity) throws Exception {
        LOG.info("OAuth2, enable security login");

        return httpSecurity
            .oauth2Login()
            .and();
    }

}

