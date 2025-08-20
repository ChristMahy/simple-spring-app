package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.GeneratorConstants;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Component
@Order(GeneratorConstants.UserGeneratorExecutionOrder.RIGHT)
public class RightGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(RightGenerator.class);

    private final RightRepository<Right> rightRepository;
    private final RightBuilderFactory<Right> rightBuilderFactory;

    public RightGenerator(
        RightRepository rightRepository,
        RightBuilderFactory rightBuilderFactory
    ) {
        this.rightRepository = rightRepository;
        this.rightBuilderFactory = rightBuilderFactory;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationStartedEvent event) {

        try {
            // TODO: Pourquoi ne pas les mettres dans un fichier json ou autre, idem pour les autres generateurs ?
            Stream.of("ingredient:read", "ingredient:write", "ingredient:delete")
                .forEach(name -> {
                    if (rightRepository.findByName(name).isEmpty()) {
                        Right right = rightBuilderFactory.create()
                            .name(name)
                            .build();

                        right = rightRepository.save(right);

                        LOG.info("Right saved: {}", right);
                    }
                });
        } catch (Exception any) {
            throw new RuntimeException(any);
        }

    }
}
