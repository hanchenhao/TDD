package com.github.tddexample;

import lombok.val;

import java.lang.reflect.Parameter;
import java.util.*;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        val constructor = optionsClass.getDeclaredConstructors()[0];
        val arguments = Arrays.asList(args);
        try {
            val values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parseArguments(arguments, parameter)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseArguments(List<String> arguments, Parameter parameter) {
        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    final private static Map<Class<?>, ArgumentParser> PARSERS = Map.of(boolean.class,
            new SingleValuedParser(false, (it) -> Objects.equals(it, "-l"), 0),
            int.class, new SingleValuedParser(0, Integer::valueOf, 1),
            String.class, new SingleValuedParser("", String::valueOf, 1));
}
