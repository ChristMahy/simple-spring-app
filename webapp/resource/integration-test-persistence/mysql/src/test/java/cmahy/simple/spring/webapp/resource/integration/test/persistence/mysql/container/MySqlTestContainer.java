package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.exception.MySqlTestContainerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;

import static cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerConstant.*;

public class MySqlTestContainer extends MySQLContainer<MySqlTestContainer> {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlTestContainer.class);

    protected MySqlTestContainer() {

        super("mysql:lts");

        this
            .withEnv("MYSQL_ROOT_PASSWORD", "sa")
            .withUsername("sa")
            .withPassword("sa")
            .withDatabaseName(MySqlTestContainerConstant.KEYSPACE)
            .withReuse(false);

    }

    @Override
    public void start() {

        super.start();

        try {

            this.execInContainer("mkdir", "-p", CONTAINER_BACKUP_FILE_DIRECTORY);
            this.execInContainer("touch", CONTAINER_BACKUP_FILE_SQL);

        } catch (IOException | InterruptedException e) {
            throw new MySqlTestContainerException("MySQL start failure", e);
        }

        String jdbcUrl = this.getJdbcUrl().replace("/" + KEYSPACE, "/information_schema");
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

                while (rs.next()) {
                    if (StringUtils.equalsIgnoreCase(KEYSPACE, rs.getString(1))) {
                        databaseExists = true;
                        break;
                    }
                }

                if (databaseExists) {
                    System.out.println("Database `" + KEYSPACE + "` is now available.");

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

            throw new MySqlTestContainerException("Timeout: Database `" + KEYSPACE + "` not found after " + timeout.getSeconds() + " seconds");

        }

    }

}
