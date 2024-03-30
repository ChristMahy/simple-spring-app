package cmahy.common.entity.page;

import java.util.Collection;

public interface EntityPage<T> {

    Collection<T> content();

    Long totalElements();
}
