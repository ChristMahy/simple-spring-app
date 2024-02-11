package cmahy.brokers.consumer.core.adapter.api.message;

import cmahy.brokers.consumer.message.api.MessageUriConstant;
import cmahy.brokers.consumer.message.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.consumer.core.application.message.repository.MessageRepository;
import cmahy.brokers.consumer.core.domain.message.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static cmahy.common.helper.Generator.generateAString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageApiImplIntegrationTest {

    @Inject
    private MessageRepository repository;

    @Inject
    private ObjectMapper jsonMapper;

    @Inject
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        repository.save(new Message(
            null,
            LocalDateTime.now(),
            generateAString(),
            null
        ));

        repository.save(new Message(
            null,
            LocalDateTime.now(),
            generateAString(),
            null
        ));
    }

    @AfterEach
    void tearDown() {
        repository.allMessages().stream()
            .map(Message::id)
            .forEach(repository::deleteById);
    }

    @Test
    void allMessages() {
        assertDoesNotThrow(() -> {
            mockMvc.perform(
                get(MessageUriConstant.Message.BASE)
            )
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((result) -> {
                    final var messagesFromRepo = repository.allMessages().stream()
                        .sorted(Comparator.comparing(Message::id))
                        .toList();

                    final var messages = jsonMapper.readValue(
                        result.getResponse().getContentAsByteArray(),
                        new TypeReference<List<MessageOutputApiVo>>() {
                        }
                    ).stream()
                        .sorted(Comparator.comparingLong(m -> m.id().value()))
                        .toList();

                    assertThat(messages).isNotNull();
                    assertThat(messages).hasSize(messagesFromRepo.size());

                    Message fromRepo;
                    MessageOutputApiVo fromRequest;

                    for (var index = 0; index < messagesFromRepo.size(); index++) {
                        fromRepo  = messagesFromRepo.get(index);
                        fromRequest = messages.get(index);

                        assertThat(fromRepo.id()).isEqualTo(fromRequest.id().value());
                        assertThat(fromRepo.message()).isEqualTo(fromRequest.message());
                        assertThat(fromRepo.createdAt()).isEqualTo(fromRequest.createdAt());
                        assertThat(fromRepo.modificationCounter()).isEqualTo(fromRequest.modificationCounter());
                    }
                });
        });
    }
}