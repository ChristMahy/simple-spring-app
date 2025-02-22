package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.webapp.resource.impl.application.stream.visitor.StreamConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class StreamVisitorImplTest {

    @InjectMocks
    private StreamVisitorImpl streamVisitor;

    @Mock
    private StreamConsumer streamConsumer;

    @Test
    void prepareCalled_thenContentDispositionContainsFileName() {
        assertDoesNotThrow(() -> {
            var fileNameExpected = Generator.generateAString();

            streamVisitor.prepare(fileNameExpected);

            ResponseEntity<StreamingResponseBody> actual = streamVisitor.build(streamConsumer);

            assertThat(actual).isNotNull();

            assertThat(actual.getHeaders()).containsKey(HttpHeaders.CONTENT_DISPOSITION);
            assertThat(actual.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION)).hasSize(1);
            assertThat(actual.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION)).contains(fileNameExpected);
        });
    }

    @Test
    void prepareNotCalled_thenContentDispositionIsNotFilled() {
        assertDoesNotThrow(() -> {
            ResponseEntity<StreamingResponseBody> actual = streamVisitor.build(streamConsumer);

            assertThat(actual).isNotNull();

            assertThat(actual.getHeaders()).doesNotContainKey(HttpHeaders.CONTENT_DISPOSITION);
        });
    }

    @Test
    void contentType_thenContentIsDefined() {
        assertDoesNotThrow(() -> {
            var contentTypes = List.of(
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_CBOR_VALUE,
                MediaType.APPLICATION_PDF_VALUE,
                MediaType.APPLICATION_NDJSON_VALUE,
                MediaType.APPLICATION_PROTOBUF_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MediaType.TEXT_HTML_VALUE,
                MediaType.TEXT_MARKDOWN_VALUE,
                MediaType.TEXT_PLAIN_VALUE,
                MediaType.TEXT_XML_VALUE
            );

            var contentTypeExpected = contentTypes.get(Generator.randomInt(0, contentTypes.size() - 1));

            streamVisitor.contentType(contentTypeExpected);

            ResponseEntity<StreamingResponseBody> actual = streamVisitor.build(streamConsumer);

            assertThat(actual).isNotNull();

            assertThat(actual.getHeaders()).containsKey(HttpHeaders.CONTENT_TYPE);
            assertThat(actual.getHeaders().get(HttpHeaders.CONTENT_TYPE)).hasSize(1);
            assertThat(actual.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(contentTypeExpected);
        });
    }

    @Test
    void contentTypeEmpty_thenIsFilledWithOctetStream() {
        assertDoesNotThrow(() -> {
            ResponseEntity<StreamingResponseBody> actual = streamVisitor.build(streamConsumer);

            assertThat(actual).isNotNull();

            assertThat(actual.getHeaders()).containsKey(HttpHeaders.CONTENT_TYPE);
            assertThat(actual.getHeaders().get(HttpHeaders.CONTENT_TYPE)).hasSize(1);
            assertThat(actual.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        });
    }

    @Test
    void build() {
        assertDoesNotThrow(() -> {
            ResponseEntity<StreamingResponseBody> actual = streamVisitor.build(streamConsumer);

            assertThat(actual).isNotNull();

            assertThat(actual.getHeaders()).containsKey(HttpHeaders.CACHE_CONTROL);
            assertThat(actual.getHeaders().get(HttpHeaders.CACHE_CONTROL)).hasSize(1);
            assertThat(actual.getHeaders().getFirst(HttpHeaders.CACHE_CONTROL)).isEqualTo("max-age=0, must-revalidate");

            assertThat(actual.getHeaders()).containsKey(HttpHeaders.PRAGMA);
            assertThat(actual.getHeaders().get(HttpHeaders.PRAGMA)).hasSize(1);
            assertThat(actual.getHeaders().getFirst(HttpHeaders.PRAGMA)).isEqualTo("no-cache");

            assertThat(actual.getBody()).isNotNull();
            assertThat(actual.getBody()).isInstanceOf(StreamingResponseBody.class);
        });
    }
}