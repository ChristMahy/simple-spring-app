package cmahy.simple.spring.webapp.resource.integration.test.persistence.h2;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(H2ITDatasourceSnapshot.class)
public class H2ITDatasourceSnapshotConfigurer {
}
