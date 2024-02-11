package cmahy.brokers.consumer.core.adapter.message.repository;


import cmahy.brokers.consumer.core.domain.message.Message;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static cmahy.common.helper.Generator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class MessageRepositoryImplTest {

    private MessageRepositoryImpl repository;

    private List<Message> messagesSortedById;

    @BeforeEach
    void setUp() {
        repository = new MessageRepositoryImpl();
    }

    @Test
    void allMessages() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            List<Message> actual = repository.allMessages().stream()
                .sorted(Comparator.comparing(Message::id))
                .toList();

            assertThat(actual).isNotNull();
            assertThat(actual).hasSize(messagesSortedById.size());

            for (var index = 0; index < messagesSortedById.size(); index++) {
                assertThat(actual.get(index)).isEqualTo(messagesSortedById.get(index));
            }
        });
    }

    @Test
    void findById() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final var expectedMessage = messagesSortedById.get(randomInt(0, messagesSortedById.size() - 1));

            Optional<Message> actual = repository.findById(expectedMessage.id());

            assertThat(actual).isNotEmpty();
            assertThat(actual).hasValue(expectedMessage);
        });
    }

    @Test
    void findById_whenNotFound_thenReturnOptionalEmpty() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final var shouldNotBeFound = randomLong(
                messagesSortedById.stream()
                    .max(Comparator.comparing(Message::id))
                    .map(Message::id)
                    .orElse(0L),
                Long.MAX_VALUE
            );

            Optional<Message> actual = repository.findById(shouldNotBeFound);

            assertThat(actual).isEmpty();
        });
    }

    @Test
    void findByMessage() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final var expectedMessage = messagesSortedById.get(randomInt(0, messagesSortedById.size() - 1));

            Optional<Message> actual = repository.findByMessage(expectedMessage.message());

            assertThat(actual).isNotEmpty();
            assertThat(actual).hasValue(expectedMessage);
        });
    }

    @Test
    void findByMessage_whenNotFound_thenReturnOptionalEmpty() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            String messageShouldNotBeFound;
            long counter;

            do {
                messageShouldNotBeFound = generateAString(100);

                final String messageShouldNotBeFoundAsFinal = messageShouldNotBeFound;

                counter = messagesSortedById.stream()
                    .filter(m -> StringUtils.equalsIgnoreCase(m.message(), messageShouldNotBeFoundAsFinal))
                    .count();
            } while (counter > 0);

            Optional<Message> actual = repository.findByMessage(messageShouldNotBeFound);

            assertThat(actual).isEmpty();
        });
    }

    @Test
    void save_whenNotFound_thenCreateNew() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final Message newMessage = new Message(
                null,
                LocalDateTime.now(),
                generateAString(),
                randomInt(500, 1000)
            );

            final Message actual = repository.save(newMessage);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isNotNull();

            assertThat(actual.message()).isEqualTo(newMessage.message());
            assertThat(actual.createdAt()).isEqualTo(newMessage.createdAt());
            assertThat(actual.modificationCounter()).isEqualTo(newMessage.modificationCounter());

            assertThat(repository.allMessages().size()).isEqualTo(messagesSortedById.size() + 1);
        });
    }

    @Test
    void save_whenFound_thenUpdate() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final Message target = messagesSortedById.get(randomInt(0, messagesSortedById.size() - 1));
            final Message source = new Message(
                target.id(),
                LocalDateTime.now(),
                generateAString(),
                target.modificationCounter()
            );
            final int sizeBeforeUpdate = repository.allMessages().size();

            final Message actual = repository.save(source);

            assertThat(actual).isNotNull();
            assertThat(actual.id()).isEqualTo(target.id());

            assertThat(actual.message()).isEqualTo(source.message());
            assertThat(actual.createdAt()).isEqualTo(source.createdAt());
            assertThat(actual.modificationCounter()).isEqualTo(target.modificationCounter());

            assertThat(repository.allMessages().size()).isEqualTo(sizeBeforeUpdate);
        });
    }

    @Test
    void deleteById() {
        assertDoesNotThrow(() -> {
            generateSomeMessages();

            final int hasToBeDeletedSize = randomInt(0, messagesSortedById.size() / 2);
            final List<Long> hasToBeDeleted = new ArrayList<>(hasToBeDeletedSize);

            Long nextId;
            for (var cpt = 0; cpt < hasToBeDeletedSize; cpt++) {
                do {
                    nextId = messagesSortedById.get(randomInt(Math.min(2, messagesSortedById.size()), messagesSortedById.size() - 1)).id();
                } while (hasToBeDeleted.contains(nextId));

                hasToBeDeleted.add(nextId);
            }

            hasToBeDeleted.forEach(repository::deleteById);

            List<Long> actual = repository.allMessages().stream()
                .map(Message::id)
                .toList();

            assertThat(actual).isNotNull();

            assertThat(actual.stream().noneMatch(hasToBeDeleted::contains)).isTrue();
        });
    }

    private void generateSomeMessages() {
        int size = randomInt(20, 50);

        messagesSortedById = new ArrayList<>(size);

        while (--size > 0) {
            messagesSortedById.add(repository.save(new Message(
                null,
                LocalDateTime.now(),
                generateAString(),
                randomInt(500, 1000)
            )));
        }

        messagesSortedById.sort(Comparator.comparing(Message::id));
    }
}