package cmahy.simple.spring.common.converter.factory;

import cmahy.simple.spring.common.converter.StringToByteConverter;

public class StringToByteConverterFactory {

    private StringToByteConverterFactory() {}

    public static StringToByteConverter create() {
        return new StringToByteConverter();
    }
}
