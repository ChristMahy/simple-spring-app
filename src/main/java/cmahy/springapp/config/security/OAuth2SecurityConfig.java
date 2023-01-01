package cmahy.springapp.config.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import static cmahy.springapp.config.security.OAuth2ConfigConstant.PROPERTY_PREFIX;

@Conditional(
    OnOAuth2Condition.class
//    "${" + PROPERTY_PREFIX + ".enable:false} or " +
//        "${" + PROPERTY_PREFIX + ".enable:'off'} == 'on' or " +
//        "${" + PROPERTY_PREFIX + ".enable:0} == 1"
)
@Configuration
public class OAuth2SecurityConfig {
}

