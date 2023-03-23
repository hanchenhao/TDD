package com.github.tddexample;

import lombok.val;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

class ArgumentParsers implements ArgumentParser{

     private final Object defaultValue;
     private final Function<String, Object> valueParser;
     int expectedSize;

     public ArgumentParsers(Object defaultValue, java.util.function.Function<String, Object> valueParser, int expectedSize) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
        this.expectedSize = expectedSize;
    }
    static ArgumentParsers  bool(){
       return new ArgumentParsers(false, (it) -> Objects.equals(it, "-l"), 0);
    }
    static ArgumentParsers unary(Object defaultValue, java.util.function.Function<String, Object> valueParser){
         return new ArgumentParsers(defaultValue,valueParser,1);
    }

    @Override
     public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (isBooleanOptionNull(option, index)) return defaultValue;
        if (isValueSlopOver(arguments, index)) return defaultValue;
        String value = arguments.get(index + expectedSize);
        return valueParser.apply(value);
    }

    private boolean isValueSlopOver(List<String> arguments, int index) {
        val values = values(arguments, index);
        if (values.size() < expectedSize) return true;
        if (values.size() > expectedSize) throw new TooManyArgumentsException();
        return false;
    }

    private static boolean isBooleanOptionNull(Option option, int index) {
        return option.value().equals("l") && index == -1;
    }

    static List<String> values(List<String> arguments, int index) {
        int followingFlag = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).matches("^-[a-zA-Z-]+$"))
                .findFirst()
                .orElse(arguments.size());
        return arguments.subList(index + 1, followingFlag);
    }

}
