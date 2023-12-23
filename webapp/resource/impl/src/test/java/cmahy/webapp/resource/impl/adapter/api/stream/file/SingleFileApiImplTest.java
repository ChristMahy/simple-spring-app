package cmahy.webapp.resource.impl.adapter.api.stream.file;

import cmahy.common.helper.Generator;
import cmahy.webapp.resource.impl.adapter.api.stream.visitor.StreamVisitorImpl;
import cmahy.webapp.resource.impl.application.stream.query.singlefile.GenerateReadmeWithRandomContentQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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

    @InjectMocks
    private SingleFileApiImpl singleFileApi;

    @Test
    void readme() {
        assertDoesNotThrow(() -> {
            var response = mock(ResponseEntity.class);
            Optional<Boolean> onFailure;

            if (Generator.randomBoolean()) {
                onFailure = Optional.empty();
            } else {
                onFailure = Optional.of(Generator.randomBoolean());
            }

            when(query.execute(any(StreamVisitorImpl.class), eq(onFailure.orElse(Boolean.FALSE)))).thenReturn(response);

            ResponseEntity<StreamingResponseBody> actual = singleFileApi.readme(onFailure);

            assertThat(actual).isEqualTo(response);
        });
    }

    @Test
    void readme_onFailureEmpty_thenShouldReplaceWithDefaultValue() {
        assertDoesNotThrow(() -> {
            var response = mock(ResponseEntity.class);

            when(query.execute(any(StreamVisitorImpl.class), eq(Boolean.FALSE))).thenReturn(response);

            ResponseEntity<StreamingResponseBody> actual = singleFileApi.readme(Optional.empty());

            assertThat(actual).isEqualTo(response);

            verify(query).execute(any(StreamVisitorImpl.class), eq(Boolean.FALSE));
        });
    }

    @Test
    void readme_onFailureIsTrue_thenShouldTransmitToQuery() {
        assertDoesNotThrow(() -> {
            var response = mock(ResponseEntity.class);

            when(query.execute(any(StreamVisitorImpl.class), eq(Boolean.TRUE))).thenReturn(response);

            ResponseEntity<StreamingResponseBody> actual = singleFileApi.readme(Optional.of(Boolean.TRUE));

            assertThat(actual).isEqualTo(response);

            verify(query).execute(any(StreamVisitorImpl.class), eq(Boolean.TRUE));
        });
    }
}