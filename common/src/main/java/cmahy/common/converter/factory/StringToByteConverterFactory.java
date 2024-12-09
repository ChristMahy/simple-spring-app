package cmahy.common.converter.factory;

import cmahy.common.converter.StringToByteConverter;

public class StringToByteConverterFactory {

    private StringToByteConverterFactory() {}

    public static StringToByteConverter create() {
        return new StringToByteConverter();
    }
}
