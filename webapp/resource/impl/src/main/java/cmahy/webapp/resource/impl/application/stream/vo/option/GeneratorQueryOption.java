package cmahy.webapp.resource.impl.application.stream.vo.option;

import java.util.Objects;
import java.util.Optional;

public record GeneratorQueryOption(
    Boolean onFailure,
    Optional<Integer> stringSize,
    Optional<Integer> elementsSize
) {
    private static final Integer OPTION_DEFAULT_ELEMENTS_SIZE = 1024;
    private static final Integer OPTION_DEFAULT_STRING_SIZE = 5 * 1024;

    public Integer elementsSizeOrDefaultAboveZero() {
        return elementsSizeOrDefaultAboveZero(OPTION_DEFAULT_ELEMENTS_SIZE);
    }

    public Integer elementsSizeOrDefaultAboveZero(Integer defaultSize) {
        var defaultElementsSize = elementsSize().orElse((defaultSize == null) ? OPTION_DEFAULT_ELEMENTS_SIZE : defaultSize);

        return defaultElementsSize > 0 ? defaultElementsSize : OPTION_DEFAULT_ELEMENTS_SIZE;
    }

    public Integer stringSizeOrDefaultAboveZero() {
        return stringSizeOrDefaultAboveZero(OPTION_DEFAULT_STRING_SIZE);
    }

    public Integer stringSizeOrDefaultAboveZero(Integer defaultSize) {
        var defaultStringSize = stringSize().orElse(Objects.isNull(defaultSize) ? (OPTION_DEFAULT_STRING_SIZE) : defaultSize);

        return defaultStringSize > 0 ? defaultStringSize : (OPTION_DEFAULT_STRING_SIZE);
    }
}
