package com.github.tddexample;

import java.util.List;

class StringParser implements ArgumentParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.size() == 1 ? "" : String.valueOf(arguments.get(index + 1));
    }
}
