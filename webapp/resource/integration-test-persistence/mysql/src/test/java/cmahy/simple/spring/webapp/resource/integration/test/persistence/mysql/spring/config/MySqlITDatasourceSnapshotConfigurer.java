package cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.config;

import cmahy.simple.spring.webapp.resource.integration.test.persistence.mysql.spring.service.MySqlITDatasourceSnapshot;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(MySqlITDatasourceSnapshot.class)
public class MySqlITDatasourceSnapshotConfigurer {

}
