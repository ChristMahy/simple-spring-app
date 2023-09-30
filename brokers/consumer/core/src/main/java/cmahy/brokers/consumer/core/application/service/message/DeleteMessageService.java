package cmahy.brokers.consumer.core.application.service.message;

import cmahy.brokers.consumer.core.domain.Message;
import jakarta.inject.Named;

@Named
public class DeleteMessageService {
    public void execute(Message message) {
        throw new IllegalStateException("Not yet implemented !");
    }
}
