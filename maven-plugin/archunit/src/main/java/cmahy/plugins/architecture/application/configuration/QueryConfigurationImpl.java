package cmahy.plugins.architecture.application.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public class QueryConfigurationImpl implements QueryConfiguration {

    public Optional<Boolean> active = Optional.empty();

    public Optional<Boolean> getActive() {
        return active;
    }

    public QueryConfigurationImpl setActive(Boolean active) {
        this.active = Optional.ofNullable(active);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("active", getActive().orElse(null))
            .toString();
    }

    @Override
    public Optional<Boolean> active() {
        return this.getActive();
    }

}
