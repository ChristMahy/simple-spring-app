package cmahy.webapp.resource.impl.adapter.api.stream.zip;

import cmahy.webapp.resource.impl.adapter.api.stream.factory.GeneratorOptionsFactory;
import cmahy.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorFactory;
import cmahy.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorImpl;
import cmahy.webapp.resource.impl.application.stream.query.zip.GenerateZipWithRandomContentQuery;
import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZipApiImplTest {

    @Mock
    private GenerateZipWithRandomContentQuery query;

    @Mock
    private GeneratorOptionsFactory optionsFactory;

    @Mock
    private StreamVisitorFactory streamVisitorFactory;

    @InjectMocks
    private ZipApiImpl zipApiImpl;

    @Test
    void makeAZip() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.zip(Boolean.FALSE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = zipApiImpl.makeAZip(Optional.of(Boolean.FALSE));

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).zip(Boolean.FALSE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }

    @Test
    void makeAZip_onFailureEmpty_thenShouldReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.zip(Boolean.FALSE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = zipApiImpl.makeAZip(Optional.empty());

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).zip(Boolean.FALSE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }

    @Test
    void makeAZip_onFailureIsTrue_thenShouldTransmitToQuery() {
        assertDoesNotThrow(() -> {
            var streamVisitor = mock(StreamVisitorImpl.class);
            var options = mock(GeneratorQueryOption.class);
            var response = mock(ResponseEntity.class);

            when(optionsFactory.zip(Boolean.TRUE)).thenReturn(options);
            when(streamVisitorFactory.create()).thenReturn(streamVisitor);

            when(query.execute(streamVisitor, options)).thenReturn(response);

            var actual = zipApiImpl.makeAZip(Optional.of(Boolean.TRUE));

            assertThat(actual).isEqualTo(response);

            verify(optionsFactory).zip(Boolean.TRUE);
            verify(query).execute(streamVisitor, options);

            verifyNoMoreInteractions(optionsFactory);
            verifyNoMoreInteractions(query);
        });
    }
}