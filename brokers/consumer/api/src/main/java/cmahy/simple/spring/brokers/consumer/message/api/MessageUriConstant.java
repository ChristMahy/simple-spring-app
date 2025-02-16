package cmahy.simple.spring.brokers.consumer.message.api;

public final class MessageUriConstant {
    private MessageUriConstant() {}

    public static final String BASE = "/api/v1";

    public static final class Message {
        private Message() {}

        public static final String BASE = MessageUriConstant.BASE + "/message";
    }

    public static final class Zip {
        private Zip() {}

        public static final String BASE = MessageUriConstant.BASE + "/a-zip";
    }

    public static  final class Readme {
        private Readme() {}

        public static final String BASE = MessageUriConstant.BASE + "/readme";
    }
}
