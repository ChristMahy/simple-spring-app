package cmahy.simple.spring.webapp.resource.cassandrastarter.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackageClasses = {CassandraConverterConfigurer.class})
public class CassandraStarterConfigurer {
}
