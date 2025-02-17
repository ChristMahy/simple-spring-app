package cmahy.simple.spring.brokers.consumer.kafka.listener;


import cmahy.simple.spring.brokers.consumer.message.event.DeletionMessageListener;
import cmahy.simple.spring.brokers.consumer.message.event.vo.id.MessageEventId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageDeleteKafkaListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageDeleteKafkaListener.class);

    private final DeletionMessageListener listener;
    private final ObjectMapper jsonMapper;

    public MessageDeleteKafkaListener(
        DeletionMessageListener listener,
        ObjectMapper jsonMapper
    ) {
        this.listener = listener;
        this.jsonMapper = jsonMapper;
    }

    @KafkaListener(topics = "message.queue.delete")
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
                jsonMapper.readValue(message, MessageEventId.class)
            );
        } catch (Exception any) {
            LOG.error(any.getMessage(), any);
        }
    }
}
