package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RightGeneratorInputVo(
    String name
) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public RightGeneratorInputVo {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .toString();
    }

}
