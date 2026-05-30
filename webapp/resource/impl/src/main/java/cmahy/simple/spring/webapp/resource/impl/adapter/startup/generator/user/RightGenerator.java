package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.ApplicationProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.OnStartProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.config.properties.start.ResourcesProperties;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user.vo.input.RightGeneratorInputVo;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
@Order(GeneratorConstants.UserGeneratorExecutionOrder.RIGHT)
public class RightGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RightGenerator.class);

    private final RightRepository<Right> rightRepository;
    private final RightBuilderFactory<Right> rightBuilderFactory;
    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public RightGenerator(
        RightRepository rightRepository,
        RightBuilderFactory rightBuilderFactory,
        ApplicationProperties applicationProperties,
        ObjectMapper objectMapper
    ) {
        this.rightRepository = rightRepository;
        this.rightBuilderFactory = rightBuilderFactory;
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {

            Optional<Resource> rightsResource = Optional.ofNullable(applicationProperties.onStart())
                .map(OnStartProperties::resources)
                .map(ResourcesProperties::rights);

            if (rightsResource.map(Resource::exists).orElse(Boolean.FALSE)) {

                try (InputStream rightsStream = rightsResource.get().getInputStream()) {
                    List<RightGeneratorInputVo> rights = objectMapper.readValue(rightsStream, new TypeReference<>() {
                    });

                    rights.forEach(input -> {
                        if (rightRepository.findByName(input.name()).isEmpty()) {
                            Right right = rightBuilderFactory.create()
                                .name(input.name())
                                .build();

                            right = rightRepository.save(right);

                            LOG.info("<{}> saved <{}>", right.getClass().getSimpleName(), right);
                        }
                    });
                }
            }

        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }
}
