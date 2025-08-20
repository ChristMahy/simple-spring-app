package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

enum SnapshotSingleton {

    SNAPSHOT;

    private final Logger LOG = LoggerFactory.getLogger(SnapshotSingleton.class);

    private Boolean created = Boolean.FALSE;
    private DatasourceInfo datasource;
    private final File directory;

    SnapshotSingleton() {
        try {

            this.directory = Files
                .createTempDirectory("snapshot-h2-" + UUID.randomUUID().toString().toLowerCase())
                .toFile();

            LOG.info("Creating snapshot directory <{}>", directory.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean created() {
        return created;
    }

    public File directory() {
        return directory;
    }

    public void create(ApplicationContext applicationContext) {
        LOG.info("Creating snapshot for H2TestContainer");

        Environment environment = applicationContext.getEnvironment();

        this.datasource = new DatasourceInfo(
            Optional
                .ofNullable(environment.getProperty("spring.datasource.url"))
                .or(() ->
                    Optional
                        .ofNullable(environment.getProperty("spring.datasource.name"))
                        .map(url -> "jdbc:h2:mem:" + url)
                )
                .orElseThrow(),
            Optional
                .ofNullable(environment.getProperty("spring.datasource.username"))
                .orElse(""),
            Optional
                .ofNullable(environment.getProperty("spring.datasource.password"))
                .orElse("")
        );

        LOG.info("Datasource URL <{}>", datasource);

        try (Connection connection = DriverManager.getConnection(datasource.url(), datasource.username(), datasource.password())) {

            PreparedStatement stmt = connection.prepareStatement("SCRIPT TO ?");

            stmt.setString(1, directory().getAbsolutePath().replaceAll("\\\\\\\\", "/") + "/backup.sql");

            stmt.execute();

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

        this.created = Boolean.TRUE;
    }

    public void restore() {

        LOG.info("Restoring snapshot for H2TestContainer");

        try (Connection connection = DriverManager.getConnection(datasource.url(), datasource.username(), datasource.password())) {

            Statement statement = connection.createStatement();

            statement.execute("DROP ALL OBJECTS");

            statement.close();

            PreparedStatement stmt = connection.prepareStatement("RUNSCRIPT FROM ?");

            stmt.setString(1, directory().getAbsolutePath().replaceAll("\\\\\\\\", "/") + "/backup.sql");

            stmt.execute();

            stmt.close();

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

    private record DatasourceInfo(
        String url,
        String username,
        String password
    ) {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("url", url())
                .append("username", username())
                .append("password", password())
                .toString();
        }

    }

}
