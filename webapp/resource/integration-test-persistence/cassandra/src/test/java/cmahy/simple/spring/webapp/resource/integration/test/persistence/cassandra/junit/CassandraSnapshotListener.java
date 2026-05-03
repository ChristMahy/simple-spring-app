package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.junit;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra.spring.service.CassandraITKeyspaceSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.Objects;

public class CassandraSnapshotListener extends AbstractTestExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraSnapshotListener.class);

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {

        ApplicationContext applicationContext = testContext.getApplicationContext();

        if (Objects.nonNull(applicationContext)) {

            CassandraITKeyspaceSnapshot keyspaceSnapshot = applicationContext.getBean(CassandraITKeyspaceSnapshot.class);

            keyspaceSnapshot.restoreSnapshot();

        }

    }

}
