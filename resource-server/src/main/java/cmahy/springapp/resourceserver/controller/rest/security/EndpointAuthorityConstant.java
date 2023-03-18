package cmahy.springapp.resourceserver.controller.rest.security;

public final class EndpointAuthorityConstant {
    private EndpointAuthorityConstant() {}

    public static final class Ingredient {
        private Ingredient() {}

        public static final String WRITE = "ingredient.write";
        public static final String DELETE = "ingredient.delete";
    }
}
