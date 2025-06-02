package cmahy.simple.spring.webapp.reactive.resource.impl.vo.input;

import java.util.Optional;

public record TodoUpdateInputVo(
    Optional<String> title,
    Optional<String> description
) {
}
