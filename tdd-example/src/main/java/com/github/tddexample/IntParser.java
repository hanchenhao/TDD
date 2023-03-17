package com.github.tddexample;

import java.util.List;

class IntParser implements ArgumentParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return arguments.size() == 1 ? 0 : Integer.parseInt(arguments.get(index + 1));
    }
}
