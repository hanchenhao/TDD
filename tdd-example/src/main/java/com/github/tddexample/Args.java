package com.github.tddexample;

import lombok.val;

import java.util.Arrays;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        val constructor = optionsClass.getDeclaredConstructors()[0];
        try {
            val parameter = constructor.getParameters()[0];
            val option = parameter.getAnnotation(Option.class);
            val arguments = Arrays.asList(args);
            Object value = null;

            if (parameter.getType() == boolean.class) {
                value = arguments.contains("-" + option.value());
            }
            if (parameter.getType() == int.class) {
                int index = arguments.indexOf("-" + option.value());
                value = arguments.size() == 1 ? 0 : Integer.parseInt(arguments.get(index + 1));
            }
            if (parameter.getType() == String.class) {
                int index = arguments.indexOf("-" + option.value());
                value = arguments.size() == 1 ? "" : String.valueOf(arguments.get(index + 1));
            }
            return (T) constructor.newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
