package com.github.tddexample;

import lombok.val;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class OptionClass<T> {
    Class<T> optionsClass;
    Map<Class<?>, ArgumentParser> parsers;

    public OptionClass(Class<T> optionsClass, Map<Class<?>, ArgumentParser> parsers) {
        this.optionsClass = optionsClass;
        this.parsers = parsers;
    }

    protected T optionParse(String[] args) {
        val constructor = this.optionsClass.getDeclaredConstructors()[0];
        val arguments = Arrays.asList(args);
        try {
            val values = Arrays.stream(constructor.getParameters())
                    .map(parameter -> parseArguments(arguments, parameter)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseArguments(List<String> arguments, Parameter parameter) {
        return parsers.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }
}
