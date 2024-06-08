package cmahy.common.entity.page;

import java.util.Collection;

public interface EntityPage<T> {

    <R extends Iterable<T>> R content();

    Long totalElements();
}
