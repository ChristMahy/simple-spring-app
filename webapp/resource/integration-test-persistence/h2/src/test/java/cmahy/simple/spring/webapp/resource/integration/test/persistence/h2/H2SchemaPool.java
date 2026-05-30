//package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;
//
//
//import liquibase.*;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.exception.DatabaseException;
//import liquibase.exception.LiquibaseException;
//import liquibase.resource.ClassLoaderResourceAccessor;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//public class H2SchemaPool {
//
//    private static final BlockingQueue<String> SCHEMA_POOL = new LinkedBlockingQueue<>();
//
//    static {
//
//        for (int i = 1; i <= 10; i++) {
//
//            String schemaName = "it_test_" + i;
//
//            try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:" + schemaName)) {
//
//                createSchemaIfNeeded(connection);
//
//                runLiquibase(connection);
//
//                seedSomeData(connection);
//
//            } catch (Exception e) {
//
//                throw new RuntimeException("Failed to run Liquibase", e);
//
//            }
//
//            SCHEMA_POOL.add(schemaName);
//
//        }
//
//    }
//
//    private static void createSchemaIfNeeded(Connection connection) {
//
//        throw new IllegalStateException("Not yet implemented !");
//
//    }
//
//    private static void runLiquibase(Connection connection) throws LiquibaseException {
//
//        Liquibase liquibase = new Liquibase(
//            "db/changelog/db.changelog-master.yaml",
//            new ClassLoaderResourceAccessor(),
//            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection))
//        );
//
//        liquibase.update(new Contexts(), new LabelExpression());
//
//    }
//
//    private static void seedSomeData(Connection connection) {
//
////        throw new IllegalStateException("Not yet implemented !");
//
//    }
//
//    public static String borrow() {
//
//        try {
//
//            return SCHEMA_POOL.take();
//
//        } catch (InterruptedException e) {
//
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Interrupted while waiting for a schema", e);
//
//        }
//
//    }
//
//    public static void release(String schemaName) {
//
//        cleanUpSchema();
//        SCHEMA_POOL.add(schemaName);
//
//    }
//
//    private static void cleanUpSchema() {
//        throw new IllegalStateException("Not yet implemented !");
//    }
//
//}
