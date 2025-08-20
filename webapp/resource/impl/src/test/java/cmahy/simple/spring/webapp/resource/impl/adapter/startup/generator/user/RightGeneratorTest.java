package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RightGeneratorTest {

    @Mock
    private RightRepository<Right> rightRepository;

    @Mock
    private RightBuilderFactory<Right> rightBuilderFactory;

    @InjectMocks
    private RightGenerator rightGenerator;

    @Mock
    private ApplicationStartedEvent applicationArguments;

    @Mock(answer = Answers.RETURNS_SELF)
    private RightBuilder<Right> rightBuilder;

    @Mock
    private Right right;

    @Test
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {

            when(rightRepository.findByName(anyString())).thenReturn(Optional.empty());
            when(rightRepository.save(right)).thenReturn(right);

            when(rightBuilderFactory.create()).thenReturn(rightBuilder);
            when(rightBuilder.build()).thenReturn(right);


            rightGenerator.onApplicationEvent(applicationArguments);


            verify(rightRepository, times(3)).save(right);

        });
    }
}