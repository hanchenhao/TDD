package com.github.tddexample;

import java.util.*;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        return new OptionClass<>(optionsClass, PARSERS).optionParse(args);
    }


    final private static Map<Class<?>, ArgumentParser> PARSERS = Map.of(boolean.class,
            ArgumentParsers.bool(),
            int.class, ArgumentParsers.unary(0, Integer::valueOf),
            String.class, ArgumentParsers.unary("", String::valueOf));
}
