package cmahy.simple.spring.common.entity.page;

public interface EntityPage<T> {

    <R extends Iterable<T>> R content();

    Long totalElements();
}
