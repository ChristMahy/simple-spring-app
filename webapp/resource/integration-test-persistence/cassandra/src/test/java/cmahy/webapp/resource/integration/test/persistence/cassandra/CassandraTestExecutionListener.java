package cmahy.webapp.resource.integration.test.persistence.cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CassandraTestExecutionListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraTestExecutionListener.class);

    static {
        LOG.info("Initializing CassandraTestContainer");
    }

    public CassandraTestExecutionListener() {
        LOG.info("Starting CassandraTestExecutionListener");
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        if (testContext.hasApplicationContext()) {
            ApplicationContext applicationContext = testContext.getApplicationContext();
        }
    }
}
