package cmahy.webapp.resource.ui.taco;

public final class TacoUriConstant {

    private TacoUriConstant() {}

    public static final String BASE_URL = "/taco";

    public static final class Home {

        private Home() {}
    }

    public static final class Design {

        private Design() {}

        public static final String DESIGN_BASE_URL = BASE_URL + "/design";
    }

    public static final class ClientOrder {

        private ClientOrder() {}

        public static final String CLIENT_ORDER_BASE_URL = BASE_URL + "/client-order";
    }
}
