package cmahy.simple.spring.webapp.resource.impl.adapter.api.security;

import org.springframework.stereotype.Component;

@Component
public class PreAuthorizeScope {

    public final String GUEST = "ROLE_Guest";
    public final String ADMIN = "ROLE_Admin";
}
