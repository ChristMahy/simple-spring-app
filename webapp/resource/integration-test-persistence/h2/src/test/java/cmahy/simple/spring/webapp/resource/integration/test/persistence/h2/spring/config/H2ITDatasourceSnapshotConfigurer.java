package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.spring.config;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.h2.H2ITDatasourceSnapshot;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(H2ITDatasourceSnapshot.class)
public class H2ITDatasourceSnapshotConfigurer {
}
