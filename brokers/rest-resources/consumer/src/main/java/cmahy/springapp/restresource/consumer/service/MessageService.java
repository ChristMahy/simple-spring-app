package cmahy.springapp.restresource.consumer.service;

import cmahy.springapp.restresource.consumer.controller.vo.output.MessageOutputVO;
import cmahy.springapp.restresource.consumer.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageOutputVO> getMessages() {
        return messageRepository
            .findAll()
            .stream()
            .map(m -> new MessageOutputVO(m.message()))
            .toList();
    }
}
