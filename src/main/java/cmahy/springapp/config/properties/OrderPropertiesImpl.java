package cmahy.springapp.config.properties;

import cmahy.springapp.properties.OrderProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
public class OrderPropertiesImpl implements OrderProperties {
    private int pageSize = 20;
}
