package cmahy.webapp.resource.api.stream;

import cmahy.webapp.resource.api.UriConstant;

public final class StreamUriConstant {

    private StreamUriConstant() {}

    public static final class SingleFile {

        private SingleFile() {}

        public static final String BASE = UriConstant.BASE_V1 + "/single-file";

        public static final String README = "/readme";
    }

    public static final class Zip {

        private Zip() {}

        public static final String BASE = UriConstant.BASE_V1 + "/zip";
    }
}
