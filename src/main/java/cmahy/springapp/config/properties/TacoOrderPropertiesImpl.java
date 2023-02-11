package cmahy.springapp.config.properties;

import cmahy.springapp.properties.TacoOrderProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
public class TacoOrderPropertiesImpl implements TacoOrderProperties {
    private int pageSize = 20;
}
