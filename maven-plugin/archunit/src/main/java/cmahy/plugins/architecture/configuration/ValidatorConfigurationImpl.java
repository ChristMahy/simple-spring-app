package cmahy.plugins.architecture.configuration;

import cmahy.plugins.architecture.validator.ArchitectureType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ValidatorConfigurationImpl implements ValidatorConfiguration {

    private ArchitectureType type;

    public ArchitectureType getType() {
        return type;
    }

    public ValidatorConfigurationImpl setType(ArchitectureType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("type", getType())
            .toString();
    }
    @Override
    public ArchitectureType type() {
        return getType();
    }

}
