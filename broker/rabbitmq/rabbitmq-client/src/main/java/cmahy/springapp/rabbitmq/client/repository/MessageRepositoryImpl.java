package cmahy.springapp.rabbitmq.client.repository;

import cmahy.springapp.rabbitmq.client.domain.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageRepositoryImpl implements MessageRepository {

    final List<Message> messages = new ArrayList<>();

    @Override
    public List<Message> findAll() {
        return messages.stream().toList();
    }

    @Override
    public Message save(Message message) {
        Optional<Message> found = messages.stream().filter(m -> Objects.equals(m.message(), message.message())).findFirst();

        if (found.isEmpty()) {
            messages.add(message);

            return message;
        }

        return found.get();
    }
}
