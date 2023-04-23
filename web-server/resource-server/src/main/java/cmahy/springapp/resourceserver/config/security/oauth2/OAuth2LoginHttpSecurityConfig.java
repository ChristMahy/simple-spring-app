//package cmahy.springapp.resourceserver.config.security.oauth2;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static cmahy.springapp.resourceserver.config.security.HttpSecurityConfig.ORDER_CONFIG_OAUTH2_LOGIN;
//import static cmahy.springapp.resourceserver.config.security.SecurityConfigConstant.*;
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@ConditionalOnProperty(
//    prefix = PROPERTY_PREFIX,
//    name = OAUTH2_NAME,
//    havingValue = OAUTH2_NAME_VALUE
//)
//@Configuration
//public class OAuth2LoginHttpSecurityConfig {
//    private static final Logger LOG = LoggerFactory.getLogger(OAuth2LoginHttpSecurityConfig.class);
//
//    @Bean
//    @Order(ORDER_CONFIG_OAUTH2_LOGIN)
//    public SecurityFilterChain oauthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        LOG.info("Setup OAuth2 login configuration");
//
////        httpSecurity.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//
//        return httpSecurity.build();
//    }
//}
//
