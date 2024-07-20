package cmahy.webapp.shell.client.impl.application.vo.property;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

public record BuildPropertyVo(
    String group,
    String artifact,
    String projectName,
    String name,
    String version,
    Instant time,
    String javaVersion,
    String description
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("group", group)
            .append("artifact", artifact)
            .append("projectName", projectName)
            .append("name", name)
            .append("version", version)
            .append("time", time)
            .append("javaVersion", javaVersion)
            .append("description", description)
            .toString();
    }
}
