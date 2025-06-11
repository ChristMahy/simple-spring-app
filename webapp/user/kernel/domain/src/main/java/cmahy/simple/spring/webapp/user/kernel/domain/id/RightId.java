package cmahy.simple.spring.webapp.user.kernel.domain.id;

import cmahy.simple.spring.common.entity.id.EntityId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

public record RightId(
    UUID value
) implements EntityId<UUID> {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("value", value())
            .toString();
    }

}
