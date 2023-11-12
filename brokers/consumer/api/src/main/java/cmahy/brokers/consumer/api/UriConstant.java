package cmahy.brokers.consumer.api;

public final class UriConstant {
    private UriConstant() {}

    public static final String BASE = "/api/v1";

    public static final class Message {
        private Message() {}

        public static final String BASE = UriConstant.BASE + "/message";
    }

    public static final class Zip {
        private Zip() {}

        public static final String BASE = UriConstant.BASE + "/a-zip";
    }

    public static  final class Readme {
        private Readme() {}

        public static final String BASE = UriConstant.BASE + "/readme";
    }
}
