package cmahy.simple.spring.webapp.resource.impl.adapter.startup.generator.user;

import cmahy.simple.spring.webapp.user.kernel.application.repository.RightRepository;
import cmahy.simple.spring.webapp.user.kernel.application.repository.RoleRepository;
import cmahy.simple.spring.webapp.user.kernel.domain.Right;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.RightBuilder;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RightBuilderFactory;
import cmahy.simple.spring.webapp.user.kernel.domain.builder.factory.RoleBuilderFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private ApplicationArguments applicationArguments;

    @Mock(answer = Answers.RETURNS_SELF)
    private RightBuilder<Right> rightBuilder;

    @Mock
    private Right right;

    @Test
    void run() {
        assertDoesNotThrow(() -> {

            when(rightRepository.findByName(anyString())).thenReturn(Optional.empty());
            when(rightRepository.save(right)).thenReturn(right);

            when(rightBuilderFactory.create()).thenReturn(rightBuilder);
            when(rightBuilder.build()).thenReturn(right);


            rightGenerator.run(applicationArguments);


            verify(rightRepository, times(3)).save(right);

        });
    }
}