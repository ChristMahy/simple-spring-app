package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container;

public enum MySqlTestContainerSingleton {

    INSTANCE;

    private final MySqlTestContainer container;

    MySqlTestContainerSingleton() {
        container = new MySqlTestContainer();
    }

    public MySqlTestContainer container() {
        return container;
    }

}
