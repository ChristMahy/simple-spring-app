package cmahy.simple.spring.brokers.publisher.kafka.config;

public final class KafkaTopic {

    private KafkaTopic() {}

    public static final class Message {

        private Message() {}

        public static final String DEFAULT = "message.queue";
        public static final String MODIFY = "message.queue.modify";
        public static final String DELETE = "message.queue.delete";
    }
}
