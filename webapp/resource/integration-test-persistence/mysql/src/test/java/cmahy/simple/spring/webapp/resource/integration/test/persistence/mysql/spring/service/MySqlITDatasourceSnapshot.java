package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.service;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.Container;

import java.io.IOException;
import java.util.List;

import static cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerConstant.DATABASE_NAME_PROPERTY_KEY;

@Service
public class MySqlITDatasourceSnapshot {

    private final Logger LOG = LoggerFactory.getLogger(MySqlITDatasourceSnapshot.class);

    private final Environment environment;

    public MySqlITDatasourceSnapshot(Environment environment) {
        this.environment = environment;
    }

    public void create() throws IOException, InterruptedException {

        LOG.info("Creating snapshot for MySqlTestContainer");

        String dbName = environment.getProperty(DATABASE_NAME_PROPERTY_KEY);

        MySqlTestContainer containerInstance = MySqlTestContainerSingleton.INSTANCE.container();

        String dumpCommand = String.format(
            "mysqldump -u%s -p%s --databases %s > %s",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            dbName,
            MySqlTestContainerConstant.CONTAINER_BACKUP_FILE_DIRECTORY + "/" + dbName + ".sql"
        );

        execInContainer("/bin/sh", "-c", dumpCommand);

    }

    public void restore() throws IOException, InterruptedException {

        LOG.info("Restoring snapshot for MySqlTestContainer");

        String dbName = environment.getProperty(DATABASE_NAME_PROPERTY_KEY);

        MySqlTestContainer containerInstance = MySqlTestContainerSingleton.INSTANCE.container();

        String dropCommand = String.format(
            "mysql -u%s -p%s -e 'DROP DATABASE `%s`;'",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            dbName
        );

        String restoreCommand = String.format(
            "mysql -u%s -p%s < %s",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            MySqlTestContainerConstant.CONTAINER_BACKUP_FILE_DIRECTORY + "/" + dbName + ".sql"
        );

        for (String command : List.of(dropCommand, restoreCommand)) {
            execInContainer("/bin/sh", "-c", command);
        }

    }

    private void execInContainer(String... command) throws IOException, InterruptedException {
        Container.ExecResult result = MySqlTestContainerSingleton
            .INSTANCE
            .container()
            .execInContainer(command);

        if (result.getExitCode() != 0) {
            throw new RuntimeException(String.format(
                "Command failed: %s\nStdout: %s\nStderr: %s",
                String.join(" ", command),
                result.getStdout(),
                result.getStderr()
            ));
        }
    }
}
