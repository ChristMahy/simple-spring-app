package cmahy.common.entity.page;

import java.util.Optional;

public final class EntityPageableBuilder<T extends EntityPageable> {

    private final Integer defaultPageSize;

    private Optional<Integer> pageSize;
    private Optional<Integer> pageNumber;

    private EntityPageableBuilder(Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize == null ? 10 : defaultPageSize;

        this.pageSize = Optional.ofNullable(defaultPageSize);
        this.pageNumber = Optional.empty();
    }

    public EntityPageableBuilder<T> withPageSize(Integer pageSize) {
        this.pageSize = Optional
            .ofNullable(pageSize)
            .map(l -> l < 0 ? 0 : l);

        return this;
    }

    public EntityPageableBuilder<T> withPageNumber(Integer pageNumber) {
        this.pageNumber = Optional
            .ofNullable(pageNumber)
            .map(o -> o < 0 ? 0 : pageNumber);

        return this;
    }

    public T build(Class<T> pageableClass) {
        try {
            return pageableClass
                .getDeclaredConstructor(Integer.class, Integer.class)
                .newInstance(pageNumber.orElse(0), pageSize.orElse(defaultPageSize));
        } catch (Exception any) {
            throw new IllegalStateException("Unable to build pageable entity");
        }
    }

    public static <U extends EntityPageable> EntityPageableBuilder<U> instance(Integer defaultLimit) {
        return new EntityPageableBuilder<>(defaultLimit);
    }
}
