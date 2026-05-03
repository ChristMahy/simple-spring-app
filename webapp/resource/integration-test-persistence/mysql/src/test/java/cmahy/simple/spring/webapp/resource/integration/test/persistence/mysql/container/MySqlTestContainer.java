package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.exception.MySqlTestContainerException;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.mysql.MySQLContainer;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerConstant.CONTAINER_BACKUP_FILE_DIRECTORY;


public class MySqlTestContainer extends MySQLContainer {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlTestContainer.class);

    public MySqlTestContainer() {

        super("mysql:lts");

        String defaultDbSuffixName = UUID.randomUUID().toString().replace("-", "_");

        this
//            .withCommand("--skip-log-bin --skip-ssl")
            .withCommand("--skip-log-bin")
            .withEnv("MYSQL_ROOT_PASSWORD", defaultDbSuffixName)
            .withUsername(defaultDbSuffixName.substring(0, 13))
            .withPassword(defaultDbSuffixName)
            .withDatabaseName("test_" + defaultDbSuffixName)
            .withReuse(true);

    }



    @Override
    public void start() {

        super.start();

        try {

            this.execInContainer("mkdir", "-p", CONTAINER_BACKUP_FILE_DIRECTORY);
//            this.execInContainer("touch", CONTAINER_BACKUP_FILE_SQL);

        } catch (IOException | InterruptedException e) {
            throw new MySqlTestContainerException("MySQL start failure", e);
        }

        String dbName = this.getDatabaseName();

        String jdbcUrl = this.getJdbcUrl().replace("/" + dbName, "/information_schema");
        String username = this.getUsername();
        String password = this.getPassword();

        final Instant start = Instant.now();
        final Duration timeout = Duration.ofSeconds(60);

        boolean databaseExists = false;

        while (Duration.between(start, Instant.now()).compareTo(timeout) < 0) {

            try (
                Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW DATABASES")
            ) {

                while (!databaseExists && rs.next()) {
                    if (Strings.CI.equals(dbName, rs.getString(1))) {
                        databaseExists = true;
                    }
                }

                if (databaseExists) {
                    System.out.println("Database `" + dbName + "` is now available.");

                    break;
                }

                Thread.sleep(300);

            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }

        if (!databaseExists) {

            throw new MySqlTestContainerException("Timeout: Database `" + dbName + "` not found after " + timeout.getSeconds() + " seconds");

        }

    }

}
