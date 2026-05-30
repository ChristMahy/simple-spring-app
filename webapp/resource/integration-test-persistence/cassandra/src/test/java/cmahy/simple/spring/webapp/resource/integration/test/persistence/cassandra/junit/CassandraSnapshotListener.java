package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.junit;

//import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.service.CassandraITKeyspaceSnapshot;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.api.annotation.CleanupPersistence;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.annotation.Annotation;
import java.util.*;

public class CassandraSnapshotListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraSnapshotListener.class);

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

        List<Annotation> annotations = Arrays.stream(testContext.getTestMethod().getAnnotations())
            .filter(annotation -> annotation instanceof CleanupPersistence)
            .toList();

        if (annotations.size() != 1) {
            return;
        }

        CleanupPersistence cleanupInstruction = (CleanupPersistence) annotations.getFirst();

        if (!cleanupInstruction.required()) {
            return;
        }

        ApplicationContext applicationContext = testContext.getApplicationContext();

        if (applicationContext == null) {
            LOG.warn("No ApplicationContext available in TestContext; skipping Cassandra cleanup");
            return;
        }

        try {
            // obtain CqlSession from context
            CqlSession session = null;
            try {
                session = applicationContext.getBean(CqlSession.class);
            } catch (Exception ignore) {
                // no CqlSession bean
            }

            if (session == null) {
                LOG.warn("No CqlSession bean found in ApplicationContext; cannot perform Cassandra cleanup");
                return;
            }

            // determine keyspace: prefer session's current keyspace, fallback to property
            String keyspace = session.getKeyspace().map(CqlIdentifier::asInternal).orElse(null);
            if (keyspace == null || keyspace.isBlank()) {
                Environment env = applicationContext.getEnvironment();
                keyspace = env.getProperty("spring.data.cassandra.keyspace-name");
            }

            if (keyspace == null || keyspace.isBlank()) {
                LOG.warn("No keyspace determined for Cassandra cleanup; aborting");
                return;
            }

            // list user tables for the keyspace and truncate each
            String listTablesCql = "SELECT table_name FROM system_schema.tables WHERE keyspace_name = '" + keyspace + "'";
            ResultSet rs = session.execute(listTablesCql);
            List<String> tables = new ArrayList<>();
            for (Row row : rs) {
                String table = row.getString("table_name");
                if (table != null && !table.isBlank()) {
                    tables.add(table);
                }
            }

            for (String table : tables) {
                try {
                    String cql = "TRUNCATE " + keyspace + "." + table;
                    session.execute(cql);
                    LOG.info("Truncated table {}.{}", keyspace, table);
                } catch (Exception e) {
                    LOG.error("Failed to truncate table {}.{}", keyspace, table, e);
                }
            }

        } catch (Exception e) {
            LOG.error("Error during Cassandra cleanup", e);
        }

    }

}
