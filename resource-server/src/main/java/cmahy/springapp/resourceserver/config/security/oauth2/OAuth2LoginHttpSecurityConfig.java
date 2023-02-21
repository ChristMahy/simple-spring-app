package cmahy.springapp.resourceserver.config.security.oauth2;

import cmahy.springapp.resourceserver.config.security.HttpSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import static cmahy.springapp.resourceserver.config.security.HttpSecurityConfig.ORDER_CONFIG_OAUTH2_LOGIN;
import static cmahy.springapp.resourceserver.config.security.SecurityConfigConstant.*;

@ConditionalOnProperty(
    prefix = PROPERTY_PREFIX,
    name = OAUTH2_NAME,
    havingValue = OAUTH2_NAME_VALUE
)
@Component
@Order(ORDER_CONFIG_OAUTH2_LOGIN)
public class OAuth2LoginHttpSecurityConfig implements HttpSecurityConfig {
    private static final Logger LOG = LoggerFactory.getLogger(OAuth2LoginHttpSecurityConfig.class);

    @Override
    public HttpSecurity configure(HttpSecurity httpSecurity) throws Exception {
        LOG.info("{}, setup OAuth2 login configuration", HttpSecurityConfig.class.getSimpleName());

        return httpSecurity
            .oauth2Login()
            .loginPage("/oauth2/authorization/google")
            .and();
    }
}

