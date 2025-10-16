package cmahy.plugins.architecture.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProjectConfigurationImpl implements ProjectConfiguration {

    private BuildConfigurationImpl build;

    public BuildConfigurationImpl getBuild() {
        return build;
    }

    public ProjectConfigurationImpl setBuild(BuildConfigurationImpl build) {
        this.build = build;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("build", getBuild())
            .toString();
    }

    @Override
    public BuildConfiguration build() {
        return this.getBuild();
    }

}