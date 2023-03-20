package com.github.tddexample;

import lombok.val;

import java.util.List;
import java.util.function.Function;

class SingleValuedParser implements ArgumentParser {

    final private Object defaultValue;
    final private Function<String, Object> valueParser;

    public SingleValuedParser(Object defaultValue, java.util.function.Function<String, Object> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (arguments.size() == index + 1 || arguments.get(index + 1).startsWith("-")) return defaultValue;
        if (index + 2 < arguments.size()
                && !arguments.get(index + 2).startsWith("-")) throw new TooManyArgumentsException();
        val value = arguments.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        return valueParser.apply(value);
    }
}
