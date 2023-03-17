package com.github.tddexample;

import java.util.List;

class BooleanParser implements ArgumentParser {
    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.contains("-" + option.value());
    }
}
