package cmahy.simple.spring.webapp.resource.ui.vo.output;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public record Pagination(
    List<Integer> pageNumbers,
    Integer pageSize,
    Long totalElements
) {

    public boolean isEmpty() {
        return pageNumbers() == null || pageNumbers().isEmpty();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("pageNumbers", pageNumbers)
            .append("pageSize", pageSize)
            .append("totalElements", totalElements)
            .toString();
    }
}
