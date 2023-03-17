package com.github.tddexample;

import lombok.val;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        val constructor = optionsClass.getDeclaredConstructors()[0];
        val arguments = Arrays.asList(args);
        try {
            val values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parseArguments(arguments, parameter)).toArray();
            val instance = constructor.newInstance(values);
            return (T) instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseArguments(List<String> arguments, Parameter parameter) {
        val option = parameter.getAnnotation(Option.class);
        Object value = null;
        if (parameter.getType() == boolean.class) {
            value = new BooleanParser().parse(arguments, option);
        }
        if (parameter.getType() == int.class) {
            value = new IntParser().parse(arguments, option);
        }
        if (parameter.getType() == String.class) {
            value = new StringParser().parse(arguments, option);
        }
        return value;
    }

    interface ArgumentParser {
        Object parse(List<String> arguments, Option option);
    }

    static class BooleanParser implements ArgumentParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }

    static class IntParser implements ArgumentParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return arguments.size() == 1 ? 0 : Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class StringParser implements ArgumentParser {
        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return arguments.size() == 1 ? "" : String.valueOf(arguments.get(index + 1));
        }
    }
}
