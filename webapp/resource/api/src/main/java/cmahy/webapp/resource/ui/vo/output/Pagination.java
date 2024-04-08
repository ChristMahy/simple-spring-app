package cmahy.webapp.resource.ui.vo.output;

import java.util.List;

public record Pagination(
    List<Integer> pageNumbers,
    Integer pageSize,
    Long totalElements
) {

    public boolean isEmpty() {
        return pageNumbers() == null || pageNumbers().isEmpty();
    }
}
