package cmahy.brokers.consumer.core.adapter.api.message;

import cmahy.brokers.consumer.api.message.MessageApi;
import cmahy.brokers.consumer.api.vo.output.MessageOutputApiVo;
import cmahy.brokers.consumer.core.adapter.mapper.message.MessageOutputAdapterMapper;
import cmahy.brokers.consumer.core.application.query.message.GetAllMessageQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MessageApiImpl implements MessageApi {

    private final GetAllMessageQuery getAllMessageQuery;
    private final MessageOutputAdapterMapper messageOutputAdapterMapper;

    public MessageApiImpl(
        GetAllMessageQuery getAllMessageQuery,
        MessageOutputAdapterMapper messageOutputAdapterMapper
    ) {
        this.getAllMessageQuery = getAllMessageQuery;
        this.messageOutputAdapterMapper = messageOutputAdapterMapper;
    }

    @Override
    public ResponseEntity<Iterable<MessageOutputApiVo>> allMessages() {
        return ResponseEntity.ok(
            this.getAllMessageQuery.execute().stream()
                .map(messageOutputAdapterMapper::toApiVo)
                .collect(Collectors.toSet())
        );
    }
}
