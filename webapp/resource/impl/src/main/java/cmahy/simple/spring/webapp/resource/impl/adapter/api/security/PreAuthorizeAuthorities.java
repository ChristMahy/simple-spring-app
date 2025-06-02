package cmahy.simple.spring.webapp.resource.impl.adapter.api.security;

import org.springframework.stereotype.Component;

@Component // Useful for @PreAuthorize
public final class PreAuthorizeAuthorities {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final String SCOPE_PREFIX = "SCOPE_";

    public final IngredientAuthorities ingredient = new IngredientAuthorities();

    private PreAuthorizeAuthorities() {}

    public final String GUEST = ROLE_PREFIX + "Guest";
    public final String ADMIN = ROLE_PREFIX + "Admin";

    public static final class IngredientAuthorities {

        private IngredientAuthorities() {}

        public static final String READ = SCOPE_PREFIX + "ingredient:read";
        public static final String WRITE = SCOPE_PREFIX + "ingredient:write";
        public static final String DELETE = SCOPE_PREFIX + "ingredient:delete";
    }

}
