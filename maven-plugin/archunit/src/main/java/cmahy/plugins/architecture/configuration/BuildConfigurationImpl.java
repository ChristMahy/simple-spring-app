package cmahy.plugins.architecture.configuration;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BuildConfigurationImpl implements BuildConfiguration {

    private String directory;

    public String getDirectory() {
        return directory;
    }

    public BuildConfigurationImpl setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("directory", getDirectory())
            .toString();
    }

    @Override
    public String directory() {
        return this.getDirectory();
    }

}
