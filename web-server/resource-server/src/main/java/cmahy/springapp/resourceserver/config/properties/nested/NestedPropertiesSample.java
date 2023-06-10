package cmahy.springapp.resourceserver.config.properties.nested;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NestedPropertiesSample {

    @NotBlank
    @SampleConstraint
    private String testIt;
}
