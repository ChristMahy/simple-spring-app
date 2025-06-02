package cmahy.simple.spring.webapp.shell.client.impl.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(
    scanBasePackages = {
        ShellClientApplication.BASE_PACKAGE,
        ShellClientApplication.TACO_SHOP_BASE_PACKAGE
    }
)
@ConfigurationPropertiesScan(
    basePackages = {
        ShellClientApplication.BASE_PACKAGE,
        ShellClientApplication.TACO_SHOP_BASE_PACKAGE
    }
)
@EnableConfigurationProperties
public class ShellClientApplication {

    protected static final String BASE_PACKAGE = "cmahy.simple.spring.webapp.shell.client";
    protected static final String TACO_SHOP_BASE_PACKAGE = "cmahy.simple.spring.webapp.taco.shop.kernel.application";

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(ShellClientApplication.class, args);

        SpringApplication.exit(cac);
    }
}
