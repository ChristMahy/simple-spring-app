package cmahy.simple.spring.webapp.authorization.domain.id;

import cmahy.simple.spring.common.entity.id.EntityId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record RightId(Long value) implements EntityId<Long> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("value", value())
            .toString();
    }
}
