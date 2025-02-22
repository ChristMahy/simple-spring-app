package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.file;

import cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.factory.GeneratorOptionsFactory;
import cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorFactory;
import cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorImpl;
import cmahy.simple.spring.webapp.resource.impl.application.stream.query.singlefile.GenerateReadmeWithRandomContentQuery;
import cmahy.simple.spring.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingleFileApiImplTest {

    @Mock
    private GenerateReadmeWithRandomContentQuery query;

    @Mock
    private GeneratorOptionsFactory optionsFactory;

    @Mock
    private StreamVisitorFactory streamVisitorFactory;

    @InjectMocks
    private SingleFileApiImpl singleFileApi;

    @Test
    void readme() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.singleFile(Boolean.FALSE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = singleFileApi.readme(Optional.of(Boolean.FALSE));

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).singleFile(Boolean.FALSE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }

    @Test
    void readme_onFailureEmpty_thenShouldReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.singleFile(Boolean.FALSE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = singleFileApi.readme(Optional.empty());

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).singleFile(Boolean.FALSE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }

    @Test
    void readme_onFailureIsTrue_thenShouldTransmitToQuery() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.singleFile(Boolean.TRUE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = singleFileApi.readme(Optional.of(Boolean.TRUE));

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).singleFile(Boolean.TRUE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }
}