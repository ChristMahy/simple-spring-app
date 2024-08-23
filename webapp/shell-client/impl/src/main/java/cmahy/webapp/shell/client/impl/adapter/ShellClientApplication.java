package cmahy.webapp.shell.client.impl.adapter;

import cmahy.webapp.taco.shop.adapter.webclient.EnableWebClientAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {ShellClientApplication.BASE_PACKAGE, ShellClientApplication.TACO_SHOP_BASE_PACKAGE})
@ConfigurationPropertiesScan(basePackages = {ShellClientApplication.BASE_PACKAGE, ShellClientApplication.TACO_SHOP_BASE_PACKAGE})
@EnableWebClientAdapter
public class ShellClientApplication {

    protected static final String BASE_PACKAGE = "cmahy.webapp.shell.client";
    protected static final String TACO_SHOP_BASE_PACKAGE = "cmahy.webapp.taco.shop.kernel.application";

    public static void main(String[] args) {
        ConfigurableApplicationContext cac = SpringApplication.run(ShellClientApplication.class, args);

        SpringApplication.exit(cac);
    }
}
