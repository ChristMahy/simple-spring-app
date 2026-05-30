package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

public record RoleGeneratorInputVo(
    String name,
    Set<RightGeneratorInputVo> rights
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("name", name())
            .append("rights", rights())
            .toString();
    }
    
}
