package com.github.tddexample;

import lombok.val;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

class ArgumentParsers implements ArgumentParser {

    final private Object defaultValue;
    final private Function<String, Object> valueParser;
    int expectedSize;

    public ArgumentParsers(Object defaultValue, java.util.function.Function<String, Object> valueParser, int expectedSize) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
        this.expectedSize = expectedSize;
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (option.value().equals("l") && index == -1) return defaultValue;
        val values = values(arguments, index);
        if (values.size() < expectedSize) return defaultValue;
        if (values.size() > expectedSize) throw new TooManyArgumentsException();
        String value = arguments.get(index + expectedSize);
        return parseValue(value);
    }

    List<String> values(List<String> arguments, int index) {
        int followingFlag = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).matches("^-[a-zA-Z-]+$"))
                .findFirst()
                .orElse(arguments.size());
        return arguments.subList(index + 1, followingFlag);
    }

    protected Object parseValue(String value) {
        return valueParser.apply(value);
    }
}
