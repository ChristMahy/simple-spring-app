package cmahy.webapp.resource.impl.adapter.api.stream.factory;

import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneratorOptionsFactory {

    public GeneratorQueryOption singleFile(Boolean onFailure) {
        return new GeneratorQueryOption(
            onFailure == null ? Boolean.FALSE : onFailure,
            Optional.of(5 * 1024),
            Optional.of(1024)
        );
    }

    public GeneratorQueryOption zip(Boolean onFailure) {
        return new GeneratorQueryOption(
            onFailure == null ? Boolean.FALSE : onFailure,
            Optional.of(5 * 1024 * 1024),
            Optional.of(1024)
        );
    }
}
