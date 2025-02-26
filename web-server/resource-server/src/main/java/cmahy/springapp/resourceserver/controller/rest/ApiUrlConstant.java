package cmahy.springapp.resourceserver.controller.rest;

public final class ApiUrlConstant {
    private ApiUrlConstant() {}

    public static final String BASIC_URL = "/api";

    public static final class IngredientUrl {
        private IngredientUrl() {}

        public static final String BASIC_INGREDIENT_URL = BASIC_URL + "/ingredient";
    }

    public static final class IntegrationUrl {
        private IntegrationUrl() {}

        public static final String BASIC_INTEGRATION_URL = BASIC_URL + "/integration";
    }
}
