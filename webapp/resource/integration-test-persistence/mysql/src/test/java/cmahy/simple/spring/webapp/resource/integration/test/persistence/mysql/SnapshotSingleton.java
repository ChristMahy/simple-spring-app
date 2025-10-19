package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainer;
import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Container;

import java.io.IOException;
import java.util.List;

import static cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.container.MySqlTestContainerConstant.CONTAINER_BACKUP_FILE_SQL;

public enum SnapshotSingleton {

    SNAPSHOT;

    private final Logger LOG = LoggerFactory.getLogger(SnapshotSingleton.class);

    private Boolean created = Boolean.FALSE;

    SnapshotSingleton() {}

    public Boolean created() {
        return created;
    }

    public void create() throws IOException, InterruptedException {

        LOG.info("Creating snapshot for MySqlTestContainer");

        MySqlTestContainer containerInstance = MySqlTestContainerSingleton.INSTANCE.container();

        String dumpCommand = String.format(
            "mysqldump -u%s -p%s --databases %s > %s",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            containerInstance.getDatabaseName(),
            CONTAINER_BACKUP_FILE_SQL
        );

        execInContainer("/bin/sh", "-c", dumpCommand);

        this.created = Boolean.TRUE;

    }

    public void restore() throws IOException, InterruptedException {

        LOG.info("Restoring snapshot for MySqlTestContainer");

        MySqlTestContainer containerInstance = MySqlTestContainerSingleton.INSTANCE.container();

        String dropCommand = String.format(
            "mysql -u%s -p%s -e 'DROP DATABASE `%s`;'",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            containerInstance.getDatabaseName()
        );

        String restoreCommand = String.format(
            "mysql -u%s -p%s < %s",
            containerInstance.getUsername(),
            containerInstance.getPassword(),
            CONTAINER_BACKUP_FILE_SQL
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
