package cmahy.brokers.consumer.kafka.listener;

import cmahy.brokers.consumer.event.message.ModificationMessageListener;
import cmahy.brokers.consumer.event.vo.input.MessageInputEventVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageModificationKafkaListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageModificationKafkaListener.class);

    private final ModificationMessageListener listener;
    private final ObjectMapper jsonMapper;

    public MessageModificationKafkaListener(
        ModificationMessageListener listener,
        ObjectMapper jsonMapper
    ) {
        this.listener = listener;
        this.jsonMapper = jsonMapper;
    }

    @KafkaListener(topics = "message.queue.modify")
    public void receiveMessage(
        byte[] message,
        ConsumerRecord<String, byte[]> record
    ) {
        LOG.info(
            "Received from partition {} with timestamp {}",
            record.partition(),
            record.timestamp()
        );

        try {
            listener.execute(
                jsonMapper.readValue(message, MessageInputEventVo.class)
            );
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}
