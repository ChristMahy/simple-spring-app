package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator;

public final class GeneratorConstants {

    private GeneratorConstants() {}

    public static final class UserGeneratorExecutionOrder {

        private UserGeneratorExecutionOrder() {}

        public static final int RIGHT = 1;
        public static final int ROLE = 2;
        public static final int USER = 3;

    }

    public static final class TacoGeneratorExecutionOrder {

        private TacoGeneratorExecutionOrder() {}

        public static final int INGREDIENT = 101;
        public static final int CLIENT_ORDER = 102;

    }

}
