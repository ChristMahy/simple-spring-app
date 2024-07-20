package cmahy.webapp.shell.client.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {ShellClientApplication.BASE_PACKAGE})
@ConfigurationPropertiesScan(basePackages = {ShellClientApplication.BASE_PACKAGE})
public class ShellClientApplication {

    protected static final String BASE_PACKAGE = "cmahy.webapp.shell.client";

    public static void main(String[] args) {
        SpringApplication.exit(
            SpringApplication.run(
                ShellClientApplication.class, args
            )
        );
    }
}
