package cmahy.simple.spring.webapp.reactive.resource.api.todo.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Optional;

public record TodoUpdateInputApiVo(
    Optional<String> title,
    Optional<String> description
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("title", title())
            .append("description", description())
            .toString();
    }
}
