package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class H2ITDatasourceSnapshot {

    private static final Logger LOG = LoggerFactory.getLogger(H2ITDatasourceSnapshot.class);

    private final DataSource dataSource;

    public H2ITDatasourceSnapshot(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create() {

        try (Connection connection = dataSource.getConnection()) {

            String snapshotMemName = "memFS:" + connection.getCatalog() + "_golden_dump";

            LOG.info("Starting datasource snapshot to <{}>", snapshotMemName);

            try (PreparedStatement stmt = connection.prepareStatement("SCRIPT TO ?")) {

                stmt.setString(1, snapshotMemName);

                stmt.execute();

            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }

    public void restore() {

        try (Connection connection = dataSource.getConnection()) {

            String snapshotMemName = "memFS:" + connection.getCatalog() + "_golden_dump";

            LOG.info("Starting datasource restore from <{}>", snapshotMemName);

            try (Statement st = connection.createStatement()) {

                st.execute("SET REFERENTIAL_INTEGRITY FALSE");

                st.execute("DROP ALL OBJECTS");

            }

            try (PreparedStatement stmt = connection.prepareStatement("RUNSCRIPT FROM ?")) {

                stmt.setString(1, snapshotMemName);

                stmt.execute();

            }

            try (Statement st = connection.createStatement()) {

                st.execute("SET REFERENTIAL_INTEGRITY TRUE");

            }

        } catch (Exception any) {

            LOG.warn("Failed to restore snapshot for H2TestContainer", any);

            throw new RuntimeException(any);

        }

    }

}
