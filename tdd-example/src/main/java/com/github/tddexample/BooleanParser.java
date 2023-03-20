package com.github.tddexample;

import java.util.List;

class BooleanParser implements ArgumentParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index + 1 < arguments.size()
                && !arguments.get(index + 1).startsWith("-")) throw new TooManyArgumentsException();
        return index != -1;
    }
}
