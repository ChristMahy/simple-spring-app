package cmahy.common.entity.page;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record DefaultEntityPageableImpl(
    Integer pageNumber,
    Integer pageSize
) implements EntityPageable {

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageNumber", pageNumber())
            .append("pageSize", pageSize())
            .build();
    }
}
