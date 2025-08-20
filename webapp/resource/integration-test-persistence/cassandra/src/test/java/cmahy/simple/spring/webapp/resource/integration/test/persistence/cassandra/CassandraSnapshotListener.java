package cmahy.simple.spring.webapp.resource.integration.test.persistence.cassandra;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CassandraSnapshotListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        SnapshotSingleton.SNAPSHOT.restoreSnapshot();
    }

}
